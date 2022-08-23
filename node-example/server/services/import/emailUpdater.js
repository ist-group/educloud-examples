import axios from "axios"
import fs from 'fs'
import FormData from "form-data"

export default {
    async updateEmails() {
      let file = fs.readFileSync(__dirname + "/../../../public/emails.csv")
      let formData = new FormData()
      formData.append('file', file, { type: "text/csv" })
      const config ={
        headers: {
          "Content-Type": `multipart/form-data; boundary=${formData.getBoundary()}`,
          "ist-customer-id": "SE00100",
        }
    }

      return await axios.post(
          `${process.env.IMPORT_URL}?apiKey=${process.env.IMPORT_APIKEY}`,
          formData,
          config
        )
        .then(({ data, status }) => {
          return { data: data.data, status: status };
        })
        .catch((error) => {
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log("Error", error.message);
          }
          return {
            data: error.response.data,
            status: error.response.status
          }
        });
    }
}