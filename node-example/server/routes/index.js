import express from 'express';
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  // res.render('index', { title: 'Express' });
  res.json({
    links: {
      "grades": {
        index: "/grades",
        show: "/grades/:id"
      }
    }
  })
});

export default router;
