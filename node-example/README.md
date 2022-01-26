## Node - Express example

### Grades
In [the grades routes file](./routes/grades.js) we define the endpoints for fetching grades and points to our [GradesController](./server/controllers/GradesController.js).
```javascript
import GradesController from "../controllers/GradesController"

router.get('/', GradesController.index);
router.get('/:id', GradesController.show);
```
Here we currently have two methods. One for fetching all grades and one for fetching a single grade based on the id path param.
In the controller we import the grade [service object](./server/services/educloud/grades.js) containing the code fetching the grades from EduCloud. 
```javascript
import grades from "../services/educloud/grades"

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
```
Here we create an axios object and an [authenticator object](./server/services/authenticator.js) that we use to fetch the data and return the data to the controller (which then renders it to the screen).
```javascript
async getAllGrades() {
    return await this.api.get("/grades", {
        headers: {
            "Authorization": await auth.asAuthorizationHeader()
        }
    }).then(({data}) => {
        return data.data
    }).catch(error => {
        ...
    })
},
```
The [authenticator](./server/services/authenticator.js) makes a request to skolID to fetch an access_token that we can use in our header.

