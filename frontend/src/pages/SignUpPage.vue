<template>
  <v-container class="d-flex justify-center align-center fill-height">
    <v-card class="pa-5" width="600">
      <v-card-title class="text-h5">SignUp</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="signUp">
          <v-text-field v-model="name" label="Nome" required />
          <v-text-field v-model="username" label="Email" required :rules="[v => !!v || 'Email é obrigatório', v => /.+@.+\..+/.test(v) || 'Formato de email inválido']"/>
          <v-text-field v-model="password" label="Senha" type="password" required />
          <v-btn type="submit" color="primary" block>Cadastrar</v-btn>
        </v-form>
      </v-card-text>
      <v-card-actions class="justify-center">
        <span>Já possui conta ? </span>
        <router-link to="/login">Entre aqui</router-link>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script setup>
import {ref} from "vue";
import axios from "axios";
import router from "@/router/router";

const username = ref('')
const password = ref('')
const name = ref('')

const signUp = async () => {
  await axios.post("http://localhost:8090/api/auth/signUp", {
    name: name.value,
    username: username.value,
    password: password.value
  })
      .then(response => {
        localStorage.setItem("token", response.data.token)
        router.push("/login")
      })
      .catch(err => console.log(err.message))
}
</script>