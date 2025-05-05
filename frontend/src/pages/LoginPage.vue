<template>
  <v-container class="d-flex justify-center align-center fill-height">
    <v-card class="pa-5" width="600">
      <v-card-title class="text-h5">Login</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="login">
          <v-text-field v-model="username" label="Email" required :rules="[v => !!v || 'Email é obrigatório', v => /.+@.+\..+/.test(v) || 'Formato de email inválido']"/>
          <v-text-field v-model="password" label="Senha" type="password" required />
          <v-btn type="submit" color="primary" block>Entrar</v-btn>
        </v-form>
      </v-card-text>
      <v-card-actions class="justify-center">
        <span>Não possui conta ? </span>
        <router-link to="/signUp" id="goToSignUp">Cadastre-se</router-link>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const username = ref('')
const password = ref('')
const router = useRouter()

const login = async () => {
  await axios.post('http://localhost:8090/api/auth/login', {
    username: username.value,
    password: password.value
  })
    .then(response =>{
      localStorage.setItem('token', response.data.token)
      console.log("token: " + response.data.token)
      axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`
      router.push('/home')
    })
    .catch(err => console.log(err.message))
}
</script>
