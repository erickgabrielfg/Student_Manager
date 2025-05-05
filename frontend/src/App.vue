<template>
  <v-app>
    <NavBar v-if="!$route.meta.hideNavBar"/>
    <router-view/>
  </v-app>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: 'App',
  components: {
    NavBar

  }
}
</script>

<style>
  *{
    padding: 0;
    margin: 0;
  }
</style>
