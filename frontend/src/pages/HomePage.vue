<template>
  <v-main>
    <v-container fluid>
      <v-row class="justify-center" no-gutters>
        <v-col
            v-for="user in users"
            :key="user.id"
            class="d-flex justify-center pa-3"
            cols="auto"
        >
          <UserCard
              :user="user"
              @alternar-status="changeStatus"
              @excluir="deleteUser"
          />
        </v-col>
      </v-row>
    </v-container>
    <v-container class="d-flex justify-center align-center fill-height">
      <v-card class="pa-5" width="800">
        <v-card-title class="text-h5">Cadastrar novo usuário</v-card-title>
        <v-card-text>
          <v-form @submit.prevent="addUser">
            <v-text-field v-model="userName" label="Nome" required />
            <v-text-field v-model="email" label="Email" required :rules="[v => !!v || 'Email é obrigatório', v => /.+@.+\..+/.test(v) || 'Formato de email inválido']"/>
            <v-text-field v-model="password" label="Senha" required type="password" />
            <v-select
                v-model="course"
                :items="courses"
                item-title="name"
                item-value="id"
                label="Curso"
                required
            />
            <v-switch v-model="selected" color="primary" :label="selected ? 'Ativo' : 'Inativo'" class="mt-2"></v-switch>
            <v-btn type="submit" color="primary" block>Cadastrar</v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-container>
  </v-main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import UserCard from '@/components/UserCard.vue'
import axios from "axios";

const users = ref([])
const courses = ref([])

const userName = ref("")
const email = ref("")
const password = ref("")
const course = ref("")
const selected = ref(true)

onMounted(async () => {
  const token = localStorage.getItem('token')

  await axios.get('http://localhost:8090/users', {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
      .then(response =>{
        users.value = response.data
      })
      .catch(error => console.error('Erro ao carregar usuários:', error))

  await axios.get('http://localhost:8090/courses', {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
      .then(response =>{
        courses.value = response.data
      })
      .catch(err => console.error("Erro ao carregar cursos: ", err))
})

const addUser = async () => {
  const token = localStorage.getItem("token")

  try {
    const newUser = {
      name: userName.value,
      email: email.value,
      password: password.value,
      habilitated: selected.value,
      course: {
        id: course.value
      }
    }

    const response = await axios.post("http://localhost:8090/users", newUser, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })

    const fullCourse = courses.value.find(it => it.id === course.value)
    const newUserUpdate = {...response.data, course: fullCourse}

    users.value.push(newUserUpdate)

    userName.value = ""
    email.value = ""
    password.value = ""
    course.value = ""
    selected.value = true
  }
  catch (e) {
    console.log("Erro ao cadastrar o usuário: ", e.message)
  }
}

const changeStatus = async (user) => {
  const token = localStorage.getItem('token')

  try {
    await axios.put(`http://localhost:8090/users/${user.id}`,{
      ...user,
      habilitated: !user.habilitated
    }, {
      headers:{
        Authorization: `Bearer ${token}`
      }
    })

    user.habilitated = !user.habilitated
  }
  catch (error){
    console.log("Erro ao mudar o status de um usuário: ", error.message)
  }
}

const deleteUser = async (user) => {
  const token = localStorage.getItem('token')

  if(!user.habilitated) {
    try {
      await axios.delete(`http://localhost:8090/users/${user.id}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
      users.value = users.value.filter(it => it.id !== user.id)
    } catch (e) {
      console.log("Erro ao deletar usuário: ", e.message)
    }
  }
  else {
    window.alert("Usuário ativo. Não pode ser excluído.")
  }
}
</script>
