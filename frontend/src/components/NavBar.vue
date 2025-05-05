<template>
  <v-app-bar app>
    <v-spacer/>
    <router-link to="/home" class="route">Usuários</router-link>
    <router-link to="/courses" class="route">Cursos</router-link>
    <router-link to="/login" class="route" @click="logout">LogOut</router-link>
  </v-app-bar>
</template>

<script>
export default {
  name: "NavBar",
  methods:{
    logout(){
      localStorage.removeItem('token')
    }
  }
}
</script>

<style scoped>
  .route{
    margin-right: 20px;
    text-decoration: none;
    color: #000000;
  }
  .route:hover{
    color: #494747;
  }
</style>