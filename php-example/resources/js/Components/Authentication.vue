<template>
  <div class="flex flex pr-4">
    <div class="flex items-center flex pr-4" v-if="auth">
        <span>Logged in</span>
        <button @click="logout" class="pointer bg-gray-100 p-2 ml-4 rounded-md shadow-md">Log out</button>
    </div>
    <div class="w-1/2 flex items-center justify-end" v-else>
        <div class="flex-col flex pr-4">
        <div class="py-2 flex items-center">
            <span class="w-16 inline-block">Client</span>
            <input v-model="client_id" type="text" name="client_id" class="rounded-sm text-black" />
        </div>
        <div class="py-2 flex items-center">
            <span class="w-16 inline-block">Secret</span>
            <input v-model="client_secret" type="text" name="client_secret" class="rounded-sm text-black" />
        </div>
        </div>
        <div>
        <button type="submit" class="text-center p-4 rounded-sm bg-white" @click="authenticate">
        Authenticate
        </button>
        </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data() {
    return {
        client_secret: '',
        client_id: '',
        'auth': '',
    }
  },
  methods: {
      authenticate() {
          let credentials = {
              clientId: this.client_id,
              clientSecret: this.client_secret
            }
          axios.post('/authenticate', credentials)
          .then(({data}) => {
                this.auth = true
                localStorage.setItem('token', data.access_token)
                localStorage.setItem('token_type', data.token_type)
          })
      },
      logout() {
          localStorage.removeItem('token')
          localStorage.removeItem('token_type')
          this.auth = false
      }
  },
  created() {
      if (localStorage.getItem('token')) {
          this.auth = localStorage.getItem('token')
      }
  }
}
</script>
<style>
</style>