import express from 'express';
var router = express.Router();
import GradesController from "../controllers/GradesController"

router.get('/', GradesController.index);
router.get('/:id', GradesController.show);

export default router;
