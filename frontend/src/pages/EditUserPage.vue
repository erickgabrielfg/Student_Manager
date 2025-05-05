<template>
  <v-container class="d-flex justify-center align-center fill-height">
    <v-card class="pa-5" width="600">
      <v-card-title class="text-h5">Editar usuário {{name}}</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="updateUser">
          <v-text-field v-model="name" label="Nome" required />
          <v-text-field v-model="email" label="Email" required :rules="[v => !!v || 'Email é obrigatório', v => /.+@.+\..+/.test(v) || 'Formato de email inválido']"/>
          <v-text-field v-model="password" label="Senha" type="password" required />
          <v-select
              v-model="course"
              :items="courses"
              item-title="name"
              item-value="id"
              label="Curso"
              required
          />
          <v-spacer/>
          <v-btn color="red" @click="cancelEdit" block>Cancelar</v-btn>
          <v-btn type="submit" color="primary" block>Concluir</v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>

import {onMounted, ref} from "vue";

import {useRouter} from "vue-router";
import axios from "axios";

const router = useRouter()

const id = ref(null)
const name = ref("")
const email = ref("")
const password = ref("")
const habilitated = ref(true)
const course = ref("")
const courses = ref([])

onMounted(async () =>{
  const state = window.history.state || {}
  id.value = state.id || null
  name.value = state.name || ""
  email.value = state.email || ""
  habilitated.value = state.habilitated || true

  const token = localStorage.getItem('token')

  try {
    const response = await axios.get("http://localhost:8090/courses", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })

    courses.value = response.data

    const selectedId = state.course || null

    if(selectedId) {
      const courseIsFind = courses.value.some(it => it.id === selectedId)
      course.value = courseIsFind ? selectedId : null
    }
  }
  catch(err){
    console.error("Erro ao carregar cursos: ", err)
  }
})

const updateUser = async () => {
  const token = localStorage.getItem('token')

  if (password.value === "") {
    window.alert("Insira uma senha")
  } else {

    const newUser = {
      name: name.value,
      email: email.value,
      password: password.value,
      habilitated: habilitated.value,
      course: {
        id: course.value
      }
    }

    try {
      await axios.put(`http://localhost:8090/users/${id.value}`, newUser, {
        headers:{
          Authorization: `Bearer ${token}`
        }
      })

      await router.push("/home")
    } catch (e) {
      console.log("Erro ao mudar informações do usuário: ", e.message)
    }
  }
}

const cancelEdit = () => {
  router.push("/home")
}
</script>