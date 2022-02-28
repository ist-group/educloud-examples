using EducloudExample.Models;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace EducloudExample
{
    internal class Program
    {
        
        static async Task Main(string[] args)
        {
            var config = new ConfigurationBuilder()
                .AddJsonFile("appsettings.json")
                .Build();

            var authToken = await GetAuthToken(config);
            var customerId = "REPLACE WITH YOUR CUSTOMERS ID";

            using var client = new HttpClient();

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", authToken.access_token);
            var response = await client.SendAsync(new HttpRequestMessage(HttpMethod.Get, $"https://api.ist.com/ss12000v2-api/source/{customerId}/v2.0/organisations/"));

            if (response.IsSuccessStatusCode)
            {
               var organisations =  JsonSerializer.Deserialize<ApiResponse<Organisation>>(await response.Content.ReadAsStringAsync());
               
               foreach(var organisation in organisations.data)
                {
                    Console.WriteLine($"{organisation.Id} - {organisation.Name}");
                }
            }


        }

        static async Task<AuthToken> GetAuthToken(IConfiguration configuration)
        {
            var client = new HttpClient();

           
            var formVariables = new List<KeyValuePair<string, string>>(){
                new KeyValuePair<string, string>("grant_type", "client_credentials"),
                new KeyValuePair<string, string>("client_id",configuration["skolidClientId"]),
                new KeyValuePair<string, string>("client_secret", configuration["skolidSecret"])
            };
            var content = new FormUrlEncodedContent(formVariables);

            var response = client.PostAsync("https://skolid.se/connect/token", content).GetAwaiter();
            var result = response.GetResult();
            if (!result.IsSuccessStatusCode)
            {
                throw new UnauthorizedAccessException();
            }
            var resultString = await result.Content.ReadAsStringAsync();
            var token = JsonSerializer.Deserialize<AuthToken>(resultString);
            return token;
        }
    }
}
