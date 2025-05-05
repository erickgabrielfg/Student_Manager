<template>
  <v-card class="pa-4 ma-2" rounded="xl" elevation="3" width="600">
    <v-card-title class="text-h6 justify-center">
      {{course.name}}
    </v-card-title>

    <v-card-subtitle class="text-lg-body-2">
      {{course.acronym}}
    </v-card-subtitle>
    <v-spacer/>
    <v-card-subtitle class="text-body-2">
      {{course.shift}}
    </v-card-subtitle>

    <v-card-actions class="justify-lg-space-evenly">
      <v-btn color="blue" @click="editCourse(course)">Editar</v-btn>
      <v-btn color="red" @click="$emit('excluir', course)">Excluir</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
  import {useRouter} from "vue-router";

  export default {
    name: "CourseCard",
    props: {
      course: {
        name: String,
        acronym: String,
        shift: String
      }
    },
    setup(){
      const router = useRouter()

      const editCourse = (course) =>{
        router.push({
          name: "editCourses",
          state: {
            id: course.id,
            name: course.name,
            acronym: course.acronym,
            shift: course.shift
          }
        })
      }

      return {editCourse}
    }
  }
</script>