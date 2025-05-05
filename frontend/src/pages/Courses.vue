<template>
  <v-main>
    <v-container fluid>
      <v-row class="justify-center" no-gutters>
        <v-col
            v-for="course in courses"
            :key="course.id"
            class="d-flex justify-center pa-3"
            cols="auto"
        >
          <CourseCard
            :course="course"
            @excluir="deleteCourse"
          />
        </v-col>
      </v-row>
    </v-container>
    <v-container class="d-flex justify-center align-center fill-height">
      <v-card class="pa-5" width="800">
        <v-card-title class="text-h5">Cadastrar novo curso</v-card-title>
        <v-card-text>
          <v-form @submit.prevent="addCourse">
            <v-text-field v-model="name" label="Nome" required />
            <v-text-field v-model="acronym" label="Sigla" required />
            <v-select
                v-model="shift"
                label="Turno"
                :items="['MANHA', 'TARDE', 'NOITE']"
                required
            />
            <v-btn type="submit" color="primary" block>Cadastrar</v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-container>
  </v-main>
</template>

<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import CourseCard from "@/components/CourseCard.vue";

const courses = ref([])

const name = ref("")
const acronym = ref("")
const shift = ref("")

onMounted( async () =>{
  const token = localStorage.getItem('token')

  await axios.get('http://localhost:8090/courses', {
    headers:{
      Authorization: `Bearer ${token}`
    }
  })
      .then(response =>{
        courses.value = response.data
      })
      .catch(err => console.log("Erro ao carregar cursos", err.message))
})

const addCourse = async () =>{
  const token = localStorage.getItem('token')

  try {
    const newCourse = {
      name: name.value,
      acronym: acronym.value,
      shift: shift.value
    }

    await axios.post("http://localhost:8090/courses", newCourse, {
      headers:{
        Authorization: `Bearer ${token}`
      }
    })

    name.value = ""
    acronym.value = ""
    shift.value = ""

    courses.value.push(newCourse)
  }
  catch (e) {
    console.log("Erro ao criar novo curso: ", e)
  }
}

const deleteCourse = async (course) =>{
  const token = localStorage.getItem('token')

  try {
    await axios.delete(`http://localhost:8090/courses/${course.id}`,{
      headers:{
        Authorization: `Bearer ${token}`
      }
    })

    courses.value = courses.value.filter(it => it.id !== course.id )
  }
  catch (e) {
    console.log("Erro ao excluir curso", e)
  }
}
</script>