<template>
<main>
  <h1 id="hello">모든 것이 완벽합니다 :)</h1>
  <ul>
    <li v-for="test in tests" v-bind:key="test.key" class="real-item">
      <div class="item_container">
        <div class="danger" v-bind:class="{ 'is_danger' : test.isDanger }"><p draggable="false">위험</p></div>
        <div class="light" v-bind:class="{ 'green' : (test.status && !(test.isDanger)), 'red' : (!(test.status) && !(test.isDanger)) }"></div>
        <div class="content">
          <div class="name">
            <p class="name">이름 : <input v-model="test.name" @input="changeName(test)"></p>
          </div>
          <!--<hr>-->
          <div class="stat">
            <p class="stat status_on" v-if="test.status && !(test.isDanger)" draggable="false">켜졌습니다</p>
            <p class="stat status_off" v-if="!test.status && !(test.isDanger)" draggable="false">꺼졌습니다</p>
            <p class="stat" v-if="test.callNum != null" draggable="false">찾은 횟수 : {{test.callNum}}</p>
            <p class="stat" v-if="test.callNum == null" draggable="false">찾은 횟수 정보 없음</p>
            <p class="stat" v-if="(test.connectionTime != null && test.connectionTime != -1)" draggable="false">최근 연결 : 
              <!-- <p v-if="test.connectionTime == 0">정보 없음</p> -->
              {{getWorldTime(+9)}}
            </p>
            <p class="stat" v-if="(test.connectionTime == null || test.connectionTime == -1)" draggable="false">연결 정보 없음</p>
            <p class="stat" v-if="test.ip != null && test.ip != ''" draggable="false">IP : {{test.ip}}</p>
            <p class="stat" v-if="test.ip == null || test.ip == ''" draggable="false">IP 정보 없음</p>
            <button class="stat" @click="test.isCalled = true; changeIsCalled(test)" draggable="false">호출 : {{test.isCalled}}</button>
          </div>
        </div>
      </div>
    </li>
    <PlusItem @click="showModal = true" v-if="!(tests.length == 0)"/>
    <LoadingItem v-if="(tests.length == 0)"/>
    <LoadingItem v-if="(tests.length == 0)"/>
    <LoadingItem v-if="(tests.length == 0)"/>
    <LoadingItem v-if="(tests.length == 0)"/>
    <LoadingItem v-if="(tests.length == 0)"/>
  </ul>
  <modal v-if="showModal" v-model="newItem" @close="showModal = false; addItem()" @forceClose="showModal = false"/>
</main>
</template>

<script>
import Firebase,{ firestore } from 'firebase'
import moment from 'moment'

import LoadingContainer from '../../components/libs/LoadingContainer'
import modal from '../../components/modal'
import PlusItem from './PlusItem'
import LoadingItem from './LoadingItem'

let config = {
  apiKey: "AIzaSyDqpN9M0OWICMxWLiervQkhe5KjlAvmGwc",
  authDomain: "white-stick.firebaseapp.com",
  databaseURL: "https://white-stick.firebaseio.com",
  projectId: "white-stick",
  storageBucket: "white-stick.appspot.com",
  messagingSenderId: "761920402571"
}

let app = Firebase.initializeApp(config)
let db = app.database()

let whitestick = db.ref('data')
let _first = false

whitestick.on('value', () => {
  // console.log(_first = true)
  _first = true
  // console.log(_first)
})

export default {
  name: 'ManagementPage',
  components: {
    LoadingContainer,
    modal,
    PlusItem,
    LoadingItem
  },
  firebase: function () {
    return {
      tests: whitestick
      // first: _first
    }
  },
  data () {
    return {
      newItem: {
        name: '',
        isDanger: false,
        code : 0
      },
      first: _first,
      showModal: false
    }
  },
  methods: {
    changeName(item) {
      const copy = {...item}

      delete copy['.key']
      this.$firebaseRefs.tests.child(item['.key']).set(copy)
    },
    addItem() {
      // console.log(this.newItem)
      this.newItem.callNum = 0
      this.newItem.connectionTime = -1
      this.newItem.ip = "125:0:0:1"
      this.newItem.isCalled = false
      this.newItem.status = false

      // whitestick.push(this.newItem)
      this.$firebaseRefs.tests.child(this.newItem.code).set(this.newItem)

      this.newItem.name = ''
      this.newItem.isDanger = false
      this.newItem.code = 0
    }, changeIsCalled(item) {
      const copy = {...item}

      delete copy['.key']
      this.$firebaseRefs.tests.child(item['.key']).set(copy)
    }, getWorldTime (tzOffset) { // 24시간제
      var now = moment();
      return now.format('MM월 DD일 HH시 mm분')
    }        
  }
}
</script>

<style scoped>

main {
  display: flex;
  flex-flow: column;

  justify-content: center;
  align-items: center;
}

ul {
  display: grid;

  grid-template-columns: repeat(3, 31em);
  grid-auto-rows: 38em;

  margin: 1em;
}

li {
  display: block;

  -webkit-border-radius: 9px;
  -moz-border-radius: 9px;
  border-radius: 9px;

  box-shadow: 0 6px 12px 0 hsla(0,5%,8%,.14);

  height: auto;
  margin: 3em 2em;
}

.item_container {
  display: flex;
  flex-flow: column;

  position: relative;

  height: 100%;

  justify-content: center;
  align-items: center;
}

h1#hello {
  font-size: 3em;
  margin: 1.5em;
  margin-top: 2.3em;

  width:  14em;

  color: #5a5a5a;

  animation-duration: 2.4s;
  animation-name: glow;
  animation-iteration-count: infinite;

  padding-bottom: 0.4em;
  border-bottom: 2px solid #5a5a5a;

  user-select: none;
  -moz-user-select: none;
  -webkit-user-drag: none;
  -webkit-user-select: none;
  -ms-user-select: none;
}

.content {
  display: flex;
  flex-flow: column;

  justify-content: center;
  align-items: center;

  font-size: 1.3em;
}

.danger {
  display: flex;
  flex-flow: column;

  position: absolute;
  
  top: 0.7em;
  left: 0.8em;

  width: 3em;
  height: 2em;
  border-radius: 3em;

  color: #8a8a8a;

  border: 1px solid #8a8a8a;
  background-color: white;

  justify-content: center;
  align-items: center;
}

.danger p {
  position: relative;
  top: 2px;
  width: 100%;
  height: 100%;
  margin: 0;
  
}

.danger.is_danger {
  color: white;

  border: 1px solid #ff6b4e;
  background-color: #ff6b4e;
}

.danger.not_danger {
  color: white;

  border: 1px solid #66bb6a;
  background-color: #66bb6a;
}

.light {
  display: block;
  position: absolute;
  
  top: 0.7em;
  left: calc(100% - 1.8em);

  width: 1em;
  height: 1em;
  border-radius: 0.5em;
  background-color: #8a8a8a;
}

.light.green {
  background-color: #66bb6a;
}

.light.red {
  background-color: #ff6b4e;
}

.content input {
  font-size: 1em;
  width: 7em;
}

.content hr {
  width: 10em;
  height: 1px;

  margin: 0;

  background-color: black;
}

.content > .name {
  margin-left: 2em;
  margin-right: 3em;
}

.content p.name input {
    font-size: 1em;
    width: 7em;
    height: 1.6em;
    border: none;
    /*border: 1px solid #6a6a6a;*/
    /* text-align: center; */
    /*border-radius: 0.4em;*/
    /* border-color: #ffffff; */
    padding-left: 0.3em;
    outline: none;
}

.content p.name input:focus {
  border-bottom: 1px solid #6a6a6a;
}

.content > p.stat {
  display: flex;
  flex-flow: column;

  justify-content: center;
  align-items: center;
  
  margin: 3em 1.2em;
}

.content button.stat {
  font-size: 1em;
  width: 8em;
  margin-top: 0.6em;
  border: 1px solid #2a2a2a;
  border-radius: 1em;
  background-color: white;
  transition: 300ms;

  user-select: none;
  -moz-user-select: none;
  -webkit-user-drag: none;
  -webkit-user-select: none;
  -ms-user-select: none;
}

.content button.stat:hover {
  background-color: #eaeaea;
}

.content button.stat:active {
  background-color: #cacaca;
}

.stat.status_on {
  color: #66bb6a;
}

.stat.status_off {
  color: #ff6b4e;
}

li p {
  margin: 1.4em;
  
  user-select: none;
  -moz-user-select: none;
  -webkit-user-drag: none;
  -webkit-user-select: none;
  -ms-user-select: none;
}

li p.name {
  font-size: 1.2em;
}

li p.stat {
  margin: 0.6em;
}

@keyframes glow {
  0% {
    opacity: 1;
  }
  50% {
    opacity: 0.35;
  }
  100% {
    opacity: 1;
  }
}
</style>
