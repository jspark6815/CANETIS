import Vue from 'vue'
import Vuex from 'vuex'
import VuexFire from 'vuexfire'
import Firebase from 'firebase'

Vue.use(Vuex)

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

let device_data = db.ref('data')

// VuexFire.bindFirebaseRef(device_data)

const state = {
  todos: []
}

const mutations = {
  // local state mutations
}

let store = new Vuex.Store({
  // VuexFire will check the type of the property to bind as an array or as
  // an object
  strict: true,
  state: { items: [] },
  mutations: VuexFire.firebaseMutations,
  getters: {
    items: state => state.items,
  },
  actions: {
    setItemsRef: VuexFire.firebaseAction(({ bindFirebaseRef }, ref) => {
      bindFirebaseRef('items', ref, { wait: true })
    }),
  },
})

export {
  device_data
}
export default store