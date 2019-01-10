<template>
  <header>
    <nav>
      <h1 draggable="false">CANETIS</h1>
      <p draggable="false">기기 관리</p>
      <!-- <p>당신은 준비가 되었습니까?</p> -->
    </nav>
  </header>
</template>

<script>
export default {
  
}
</script>

<style scoped>

header {
  background-color: #2a2a2a;
  width: 100%;
  box-shadow: 0 7px 28px rgba(0,0,0,0.22), 0 5px 10px rgba(0,0,0,0.18);
}

nav {
  display: flex;
  flex-flow: column;

  margin: 1.6em;

  font-family: 'Nanum Gothic', sans-serif;

  justify-content: center;
  align-items: center;
  /* align-items: flex-start; */

}

nav h1 {
  font-size: 2.2em;
  font-weight: 300;
  color: white;
  margin-bottom: 0.2em;

  user-drag: none; 
user-select: none;
-moz-user-select: none;
-webkit-user-drag: none;
-webkit-user-select: none;
-ms-user-select: none;
}

nav p {
  font-size: 1.2em;
  font-weight: 300;
  color: white;

  user-drag: none; 
user-select: none;
-moz-user-select: none;
-webkit-user-drag: none;
-webkit-user-select: none;
-ms-user-select: none;
}
</style>
