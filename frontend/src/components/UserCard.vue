<template>
  <v-card class="pa-4 ma-2" rounded="xl" elevation="3" width="300">
    <v-card-title class="text-h6 d-flex justify-space-between align-center">
      {{ user.name }}
      <v-chip :color="user.habilitated ? 'green' : 'red'" size="small" label>
        {{ user.habilitated ? 'Ativo' : 'Inativo' }}
      </v-chip>
    </v-card-title>

    <v-card-subtitle class="text-body-2">{{ user.email }}</v-card-subtitle>
    <v-card-subtitle class="text-body-2">{{ user.course?.name || "Sem curso" }}</v-card-subtitle>

    <v-card-actions class="justify-lg-space-evenly">
      <v-btn color="primary" @click="editUser(user)">Editar</v-btn>
      <v-btn
          :color="user.habilitated ? 'red' : 'green'"
          @click="$emit('alternar-status', user)"
      >
        {{ user.habilitated ? 'Desativar' : 'Ativar' }}
      </v-btn>
      <v-btn color="red" @click="$emit('excluir', user)">Excluir</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
  import {useRouter} from "vue-router";
  import {onMounted, ref} from "vue";
  import axios from "axios";

  export default {
      name: "UserCard",
      props:{
        user:{
          type: Object,
          required: true
        }
      },
      setup(){
        const router = useRouter()

        const token = localStorage.getItem('token')

        const courses = ref([])

        onMounted(async () =>{
          await axios.get('http://localhost:8090/courses', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
              .then(response =>{
                courses.value = response.data
                console.log(courses.value)
              })
              .catch(err => console.error("Erro ao carregar cursos: ", err))
        })

        const editUser = (user) =>{
          router.push({
            name: "editUsers",
            state:{
              id: user.id,
              name: user.name,
              email: user.email,
              password: user.password,
              habilitated: user.habilitated,
              course: user.course?.id || null
            }
          })
        }
        return { editUser, courses }
      }
    }
</script>