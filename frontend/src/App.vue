<template>
  <div id="app">
    <HeaderBar/>
    <ManagementPage />
  </div>
</template>

<script>
import HeaderBar from './components/layout/HeaderBar'
import ManagementPage from './components/management/ManagementPage'

export default {
  name: 'App',
  components: {
    HeaderBar,
    ManagementPage
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Nanum+Gothic');

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;

  display: flex;
  flex-flow: column;
  justify-content: center;
  align-items: center;

  font-family: 'Nanum Gothic', sans-serif;
}

</style>
