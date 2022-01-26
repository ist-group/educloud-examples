import grades from "../services/educloud/grades"
export default {

    async index(req, res, next) {
        try {
            res.json(await grades.getAllGrades());
        } catch (err) {
            console.error(`Error while getting programming languages`, err.message);
            next(err);
        }
    },

    async show(req, res, next) {
        const id = req.params.id

        try {
            res.json(await grades.getGrade(id));
        } catch (err) {
            console.error(`Error while getting programming languages`, err.message);
            next(err);
        }
    }
}