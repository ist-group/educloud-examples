# EduCloud PHP + Vue Example
This example tackles how you can integrate with EduCloud in PHP and / or Vue.
The tools used in this example is Laravel for PHP backend and Vue for frontend.

### Authentication
#### Backend
In the [AuthenticateController.php](./app/Http/Controllers/AuthenticateController.php) you can see how we get an incoming POST request to the `/authenticate` route (defined in [the routes file](./routes/web.php)) where the user posts their clientId and clientSecret as credentials.
We then use [Laravel HTTP Client](https://laravel.com/docs/8.x/http-client) to send an authentication request to [SkolID](https://skolid.se/).

```php
$response = Http::asForm()->post('https://skolid.se/connect/token', [
    'grant_type' => 'client_credentials',
    'client_id' => $request->get('clientId'),
    'client_secret' => $request->get('clientSecret')
]);

if ($response->ok()) {
    return $response;
}

return response()->json($response->collect(), Response::HTTP_UNAUTHORIZED);
```
When we get the response back we check if it's OK and return the response we get back from SkolID. If it's not we send the error data back with an unauthorized error message.

#### Frontend
The frontend for the PHP example is written in vue. We build a small form where you can enter your client- id and secret. We bind the input values with [v-model](https://vuejs.org/v2/guide/forms.html) and send it to our backend to relaying.
The source for the authentication can be found [here](./resources/js/Components/Authentication.vue).

## Fetching Persons
_This example works first after we have authenticated with SkolID. Make sure you understand how to get a token back before diving into this section._

In the [PersonsController.php](./app/Http/Controllers/EduCloud/PersonsController.php) we send a GET request to the `/educloud-persons` endpoint (again, defined in [the routes file](./routes/web.php)) with `token` as a query param.

If the token isn't passed we'll throw an exception since it's our way to authenticate toward EduCloud.
```php
if (!$request->get('token')) {
    throw new \Exception('You need to pass a token to fetch persons');
}
```
Then we let our backend make a request towards the API to fetch the persons we've got available.
```php
$persons = Http::withToken($request->get('token'), 'Bearer')
    ->get('https://api.ist.com/ss12000v2-api/source/SE00100/v2.0/persons');
```
Let's chunk up the URL a bit to make it easy to understand the different parts.

`ss12000v2-api` is the top level entrypoint for the current version of the EduCloud implementation of [SS12000](https://www.sis.se/produkter/informationsteknik-kontorsutrustning/ittillampningar/ittillampningar-inom-utbildning/ss-120002020/).

`sources/SE00100` defines which customer we are trying to fetch data from. This particular case is of course our demo customer.

`/v2.0` is what API version we want to communicate with.

`/persons` is the representation of the model that we are trying to get. For more information about our APIs, please read the [API Documentation](https://api.ist.com/ss12000v2-api/)

#### Vue Frontend
The sources for fetching persons is well documented. Check out the [Persons Component](./resources/js/Components/Persons.vue).