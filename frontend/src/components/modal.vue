<template>
<transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header">
              <h1>Header</h1>
              <button class="forceClose" @click="$emit('forceClose')"><p>X</p></button>
            </slot>
          </div>
          <hr>
          <div class="modal-body">
            <slot name="body">
              <p>이름</p><input type="text" v-model="value.name">
              <hr>
              <p>위험</p><input type="checkbox" v-model="value.isDanger">
              <hr>
              <p>번호</p><input type="number" min="1" v-model="value.code">
            </slot>
          </div>
          <hr>
          <div class="modal-footer">
            <slot name="footer">
              <button class="modal-default-button" @click="$emit('close')">
                추가
              </button>
            </slot>
          </div>
        </div>
      </div>
    </div>
</transition>
</template>

<script>
export default {
  name: 'modal',
  props: ['value']
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 300px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
  transition: all .3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header {
  display: flex;
  flex-flow: row;
  font-size: 1.3em;
}

.modal-header .forceClose {
  position: relative;
  display: flex;
  
  justify-content: center;
  align-items: center;

  margin-left: auto;
  cursor: pointer;
  font-size: 1em;
  width: auto;
  height: auto;

  border-radius: 0.2em;

  top: 1px;
  border: 1px solid #2a2a2a;
  background-color: #fafafa;
  transition: 300ms;
}

.modal-header .forceClose p {
  position: relative;

  top: 1px;
  /* left: 1px; */
}

.modal-header .forceClose:hover {
  background-color: #eaeaea;
}

.modal-header .forceClose:active {
  background-color: #cacaca;
}

.modal-footer {
  font-size: 1.2em;
}

.modal-body input[type="text"] {

}

.modal-body input[type="checkbox"] {
  width: 1.5em;
  height: 1.5em;
}

.modal-body input[type="checkbox"]:checked {

}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  font-size: 1em;
  width: 5em;
  border: 1px solid #2a2a2a;
  border-radius: 1em;
  background-color: #fafafa;
  transition: 300ms;
}

.modal-default-button:hover {
  background-color: #eaeaea;
}

.modal-default-button:active {
  background-color: #cacaca;
}
/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>