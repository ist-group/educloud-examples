import 'dotenv/config'
import express from 'express';
import path from 'path';
import cookieParser from 'cookie-parser';
import logger from 'morgan';
import indexRouter from './routes/index';
import gradesRouter from './routes/grades';
import emailRouter from './routes/emails'
var app = express();

app.use('/', indexRouter);
app.use('/grades', gradesRouter);
app.use('/emails', emailRouter);

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, '../public')));


export default app;