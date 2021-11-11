<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
    <link rel="manifest" href="/site.webmanifest">
    <title>EduCloud PHP Example</title>

    <!-- Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;600;700&display=swap" rel="stylesheet">
    <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
</head>

<body>
    <div class="min-h-screen w-screen bg-gray-100 flex flex-col" id="app">
        <header class="bg-blue-400 h-36 flex justify-between pr-4 items-center px-4">
            <nav class="text-xl">
                <a class="mr-2" href="/persons">Persons</a>
                <a href="/grades">Grades</a>
            </nav>
            <Authentication />
        </header>
        <main class="p-4">
            @yield('content')
        </main>
    </div>
    <script src="/js/app.js"></script>
</body>

</html>