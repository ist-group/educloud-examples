<template>
    <div>
        <h2 class="text-2xl">Persons</h2>
        <div>
            <button
                class="text-center p-4 rounded-md bg-blue-400"
                @click="getPersons"
            >Fetch Persons</button>
            <button
                class="text-center p-4 rounded-md bg-blue-400"
                @click="getPersonsWithBackend"
            >Fetch Persons with backend</button>
            <button
                class="text-center p-4 rounded-md bg-blue-400"
                @click="clearPersons"
            >Clear Persons</button>
        </div>
        <div v-if="persons.length > 0">
            <div v-for="person in persons" :key="person.id">
                <span>{{person.givenName}} {{person.familyName}}</span>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
        persons: [],
        eduCloudEndpoint: 'https://api.ist.com/ss12000v2-api/source/SE00100/v2.0/persons',
        endpointViaBackend: '/educloud-persons'
    }
  },
  methods: {

      /**
       * Go directly to eduCloud from the client
       * This way you'll be able to connect immediately from the client.
       */
      getPersons() {
        const auth = localStorage.getItem('token_type') == 'Bearer'
        ? `${localStorage.getItem('token_type')} ${localStorage.getItem('token')}`
        : localStorage.getItem('token')

        axios.get(this.eduCloudEndpoint, {
            headers: {
                Authorization: auth
            }
        }).then(({data}) => {
            this.persons = data.data
        })
      },

      /**
       * Offload the connection to the backend and let it handle the requests
       * towards educloud. Handy if you have some other form of authentication in place
       * where you don't want the user to always have to authenticate directly in the client
       * but have the token stored in a session.
       */
      getPersonsWithBackend() {
        let backendUrl = this.endpointViaBackend
        if (localStorage.getItem('token')) {
            backendUrl += `?token=${localStorage.getItem('token')}`
        }

        axios.get(backendUrl).then(({data}) => {
            this.persons = data.data
        })
      },
      /**
       * Just clear the Persons array.
       */
      clearPersons() {
          this.persons = []
      }
  }
}
</script>
<style>
</style>