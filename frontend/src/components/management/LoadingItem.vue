<template>
  <li class="plus-item">
    <div class="item_container animated-background">
      <p class="danger"></p>
      <p class="light"></p>
      <div class="content">
        <p class="name">
        </p>
        <hr>
        <div class="stat">
          <p class="stat"></p>
          <p class="stat"></p>
          <p class="stat"></p>
          <p class="stat"></p>
        </div>
      </div>
    </div>
  </li>
</template>

<script>
export default {
  name: 'LoadingItem'
}
</script>

<style scoped>
li {
  display: block;

  box-shadow: 0 7px 28px rgba(0,0,0,0.22), 0 5px 10px rgba(0,0,0,0.18);

  height: auto;
  margin: 3em 2em;
}

li p {
  margin: 1.4em;
  
  user-drag: none; 
  user-select: none;
  -moz-user-select: none;
  -webkit-user-drag: none;
  -webkit-user-select: none;
  -ms-user-select: none;
}

.item_container {
  display: flex;
  flex-flow: column;

  position: relative;

  height: 100%;

  justify-content: center;
  align-items: center;
}

.item_container > p {
  margin: 0;
}

.content {
  display: flex;
  flex-flow: column;

  justify-content: center;
  align-items: center;

  font-size: 1.3em;
}

.danger {
  position: absolute;
  
  top: 0.7em;
  left: 0.8em;

  width: 3em;
  height: 2em;
  border-radius: 3em;

  background-color: black;
}

.light {
  display: block;
  position: absolute;
  
  top: 0.7em;
  left: calc(100% - 1.8em);

  width: 1em;
  height: 1em;
  border-radius: 0.5em;
  background-color: black;
}

.content hr {
  width: 10em;
  height: 1px;

  margin: 1em 0;
  /* margin-top: 2em; */

  background-color: black;
}

.content > .name {
  width: 14em;
  height: 2.4em;

  background-color: black;

  margin-top: 2em;
}

div.stat {
  display: flex;
  flex-flow: column;

  justify-content: center;
  align-items: center;
}

p.stat {
  font-size: 1.4em;

  height: 1em;
  width: 5em;
  
  margin: 0.3em;

  background-color: black;  
}

p.stat:nth-child(1) {
  width: 5em;
}

p.stat:nth-child(2) {
  width: 7em;
}

p.stat:nth-child(3) {
  width: 6.5em;
}

p.stat:nth-child(4) {
  width: 6em;
}

p.stat:nth-child(5) {
  width: 6.3em;
}

/* @keyframes placeHolderShimmer {
  0%{ background-position: -468px 0; }
  100%{ background-position: 468px 0; }
}

.animated-background p, .animated-background hr {
  animation-duration: 1.5s;
  animation-fill-mode: forwards;
  animation-iteration-count: infinite;
  animation-timing-function: linear;
  animation-name: placeHolderShimmer;
  background: #f6f7f8;
  background: linear-gradient(to right, #eeeeee 8%, #dddddd 18%, #eeeeee 33%);
  background-size: 800px 104px;
} */

@keyframes placeHolderGlow {
  0% { background-color: #eeeeee}
  30% { background-color: #eeeeee}
  50% { background-color: #dddddd}
  70% { background-color: #eeeeee}
  100% { background-color: #eeeeee}
}

.animated-background p, .animated-background hr {
  animation-duration: 1.6s;
  animation-fill-mode: forwards;
  animation-iteration-count: infinite;
  animation-timing-function: linear;
  animation-name: placeHolderGlow;
}
</style>
