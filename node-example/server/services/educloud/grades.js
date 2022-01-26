import axios from "axios";
import authenticator from "../authenticator";

const auth = authenticator
let api = axios.create({
    baseURL: process.env.EDUCLOUD_URL,
    timeout: 1000,
})
export default {
    api,
    async getAllGrades() {
        return await this.api.get("/grades", {
            headers: {
                "Authorization": await auth.asAuthorizationHeader()
            }
        }).then(({data}) => {
            return data.data
        }).catch(error => {
            if (error.response) {
                console.log(error.response.data)
                console.log(error.response.status)
                console.log(error.response.headers)
            } else if (error.request) {
                console.log(error.request)
            } else {
                console.log('Error', error.message)
            }
            console.log(error.config)
        })
    },

    async getGrade(id) {
        return await this.api.get(`/grades/${id}`, {
            headers: { "Authorization": await auth.asAuthorizationHeader() }
        }).then(({data}) => {
            return data
        }).catch(error => {
            if (error.response) {
                console.log(error.response.data)
                console.log(error.response.status)
                console.log(error.response.headers)
            } else if (error.request) {
                console.log(error.request)
            } else {
                console.log('Error', error.message)
            }
            console.log(error.config)
        })
    },
}