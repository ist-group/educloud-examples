import express from "express";
var router = express.Router();
import EmailsController from "../controllers/ImportedEmailsController";

router.get("/", EmailsController.index);

export default router;
