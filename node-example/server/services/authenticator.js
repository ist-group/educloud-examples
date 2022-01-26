import axios from "axios"
import FormData from "form-data";
export default {
    clientId: process.env.EDUCLOUD_CLIENT_ID,
    clientSecret: process.env.EDUCLOUD_CLIENT_SECRET,
    grantType: process.env.EDUCLOUD_GRANTTYPE,
    url: "https://skolid.se/connect/token",
    accessToken: "",
    tokenType: "",
    scope: "",
    async authenticate() {
        if (this.accessToken !== "") return;

        let formData = new FormData()
        formData.append("client_id", this.clientId)
        formData.append("client_secret", this.clientSecret)
        formData.append("grant_type", this.grantType)

        const response = await axios({
            method: 'post',
            url: this.url,
            headers: { ...formData.getHeaders() },
            data : formData
        }).then(({data}) => {
            this.accessToken = data.access_token
            this.tokenType = data.token_type
            this.scope = data.scope
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
    token() {
        if (this.accessToken === "") {
            this.authenticate()
        }

        return this.accessToken
    },

    async asAuthorizationHeader() {
        if (this.accessToken === "") {
            await this.authenticate()
        }
        return `${this.tokenType} ${this.accessToken}`
    }
}