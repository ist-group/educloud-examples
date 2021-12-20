<?php

namespace App\Http\Controllers\EduCloud;

use \App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\Http;

class ExpandController extends Controller
{
    /**
     * When you want to fetch more data within your request you can do so by adding the query param _expand.
     * This key takes a list of enums as argument. You can find more information about this over at https://api.ist.com/ss12000v2-api
     * Since we are using persons in this case we have the following expand values to choose from:
     * duties, responsibleFor, placements, ownedPlacements, groupMemberships
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function __invoke(Request $request)
    {
        // We always need to send a token to authenticate or request.
        if (!$request->get('token')) {
            throw new \Exception('You need to pass a token to fetch persons');
        }

        /**
         * For simplicity we'll just use the duties here.
         * When we're sending lists we need to add [], otherwise it'll be intepred as a string.
         * A list with multiple choices would then look like:
         * ?_expand[]=duties&_expand[]=placements
         */
        $personsWithDuties = Http::withToken($request->get('token'), 'Bearer')
            ->get('https://api.ist.com/ss12000v2-api/source/SE00100/v2.0/persons?expand[]=duties');

        $status = $personsWithDuties->status() ? Response::HTTP_OK : $personsWithDuties->status();

        // Now we just collect the response and return it to the user.
        return response()->json($personsWithDuties->collect(), $status);
    }
}
