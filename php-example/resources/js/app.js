require('./bootstrap')
import Vue from 'vue'
import Authentication from './Components/Authentication.vue'
import Persons from './Components/Persons.vue'

Vue.component('authentication', Authentication)
Vue.component('persons', Persons)

const app = new Vue({
    el: "#app"
})