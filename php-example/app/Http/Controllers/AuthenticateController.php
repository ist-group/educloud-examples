<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\Http;

class AuthenticateController extends Controller {
    public function authenticate(Request $request) {

        $response = Http::asForm()->post('https://skolid.se/connect/token', [
            'grant_type' => 'client_credentials',
            'client_id' => $request->get('clientId'),
            'client_secret' => $request->get('clientSecret')
        ]);

        if ($response->ok()) {
            return $response;
        }

        return response()->json($response->collect(), Response::HTTP_UNAUTHORIZED);
    }
}