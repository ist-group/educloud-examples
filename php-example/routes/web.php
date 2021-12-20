<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::view('/','start');
Route::view('/persons','persons');
Route::get('/educloud-persons', [\App\Http\Controllers\EduCloud\PersonsController::class, 'index'])
    ->name('educloud-persons');

Route::get('/persons-with-duties', \App\Http\Controllers\EduCloud\ExpandController::class)
    ->name('persons-with-duties');

Route::post('authenticate', [\App\Http\Controllers\AuthenticateController::class, 'authenticate']);