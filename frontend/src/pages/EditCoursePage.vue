<template>
  <v-container class="d-flex justify-center align-center fill-height">
    <v-card class="pa-5" width="700">
      <v-card-title class="text-h5">Editar o curso de {{name}}</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="updateCourse">
          <v-text-field v-model="name" label="Nome" required />
          <v-text-field v-model="acronym" label="Sigla" required />
          <v-select
            v-model="shift"
            label="Turno"
            :items="['MANHA', 'TARDE', 'NOITE']"
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
const acronym = ref("")
const shift = ref("")

onMounted(() =>{
  const state = window.history.state || {}
  id.value = state.id || null
  name.value = state.name || ""
  acronym.value = state.acronym || ""
  shift.value = state.shift || ""
})

const updateCourse = async () =>{
  const token = localStorage.getItem('token')

  const newCourse = {
    name: name.value,
    acronym: acronym.value,
    shift: shift.value
  }

  try {
    await axios.put(`http://localhost:8090/courses/${id.value}`, newCourse, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })

    await router.push("/courses")
  }
  catch (e) {
    console.log("Erro ao editar o curso", e.message)
  }
}

const cancelEdit = () =>{
  router.push("/courses")
}
</script>