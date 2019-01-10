<template>
  <div id="loading_container">
    <div id="loading_icon">
      <div id="loading_icon_inner">
      
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoadingContainer'
}
</script>

<style scoped>
#loading_container {
  display:flex;
  justify-content: center;
  align-items: center;

  width: 100vw;
  padding: 5em;
}

#loading_icon, #loading_icon_inner * {
      box-sizing: border-box;
    }

    .pixel-spinner {
      height: 70px;
      width: 70px;
      display: flex;
      flex-direction: row;
      justify-content: center;
      align-items: center;
    }


#loading_icon {
  /* background-color: black; */
  /* width: 3em;
  height: 3em;
  border-radius: 2em;
  background: linear-gradient(to right, rgba(255,0,0,0), rgba(255,0,0,1));

  animation-name: bingle;
  animation-duration: 3s;
  animation-iteration-count: infinite; */

  height: 70px;
  width: 70px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

#loading_icon_inner {
  width: calc(70px / 7);
  height: calc(70px / 7);
  background-color: #2a2a2a;
  color: #2a2a2a;
  box-shadow: 15px 15px  0 0,
              -15px -15px  0 0,
              15px -15px  0 0,
              -15px 15px  0 0,
              0 15px  0 0,
              15px 0  0 0,
              -15px 0  0 0,
              0 -15px 0 0;
  animation: pixel-spinner-animation 2000ms linear infinite;
}
@keyframes pixel-spinner-animation {
  50% {
    box-shadow: 20px 20px 0px 0px,
                -20px -20px 0px 0px,
                20px -20px 0px 0px,
                -20px 20px 0px 0px,
                0px 10px 0px 0px,
                10px 0px 0px 0px,
                -10px 0px 0px 0px,
                0px -10px 0px 0px;
  }
  75% {
    box-shadow: 20px 20px 0px 0px,
                -20px -20px 0px 0px,
                20px -20px 0px 0px,
                -20px 20px 0px 0px,
                0px 10px 0px 0px,
                10px 0px 0px 0px,
                -10px 0px 0px 0px,
                0px -10px 0px 0px;
  }
  100% {
    transform: rotate(360deg);
  }
}
@keyframes bingle {
  0% {
    transform: rotate(0deg);
  }

  50% {
    transform: rotate(180deg);
  }

  100% {
    transform: rotate(360deg);
  }
}
</style>
