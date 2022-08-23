import emailUpdater from "../services/import/emailUpdater"

export default {
  async index(req, res, next) {
    try {
        console.log('hit email controller')
        res.json(await emailUpdater.updateEmails())
    } catch (error) {
        console.log(error)
      res.json({
        status: "error",
        message: error.message,
      });
    }
  },
};