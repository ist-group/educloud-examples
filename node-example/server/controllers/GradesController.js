import grades from "../services/educloud/grades"
export default {

    async index(req, res, next) {
        try {
            res.json(await grades.getAllGrades());
        } catch (err) {
            res.json({
                status: "error",
                message: err.message
            })
        }
    },

    async show(req, res, next) {
        const id = req.params.id

        try {
            res.json(await grades.getGrade(id));
        } catch (err) {
            res.json({
                status: "error",
                message: err.message
            })
        }
    }
}