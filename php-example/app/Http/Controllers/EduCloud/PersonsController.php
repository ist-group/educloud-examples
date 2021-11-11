<?php
namespace App\Http\Controllers\EduCloud;
use \App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\Http;

class PersonsController extends Controller {
    public function index(Request $request) {
        if (!$request->get('token')) {
            throw new \Exception('You need to pass a token to fetch persons');
        }

        $persons = Http::withToken($request->get('token'), 'Bearer')
            ->get('https://api.ist.com/ss12000v2-api/source/SE00100/v2.0/persons');

        if ($persons->ok()) {
            return $persons;
        }

        return response()->json($persons->collect(), Response::HTTP_UNAUTHORIZED);
    }
}