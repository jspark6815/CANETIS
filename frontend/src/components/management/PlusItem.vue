<template>
  <li class="plus-item">
    <img src="../../assets/ic_add_black_24px.svg" alt="plus" draggable="false">
    <h1 draggable="false">기기 추가</h1>
  </li>
</template>

<script>
export default {
  name: 'PlusItem'
}
</script>

<style scoped>
li.plus-item {
  display: flex;
  flex-flow: column;

  justify-content: center;
  align-items: center;

  cursor: pointer;

  border: 2px dashed #2a2a2a;

  height: auto;

  box-sizing: border-box;

  transition: 300ms;
}

li.plus-item:hover {
  background-color: #eaeaea;
}

li.plus-item:active {
  background-color: #cacaca;
}

li.plus-item img {
  width: 40%;
  height: 40%;
}

li.plus-item h1 {
  margin: 0.3em;
  font-size: 1.6em;
}
</style>
