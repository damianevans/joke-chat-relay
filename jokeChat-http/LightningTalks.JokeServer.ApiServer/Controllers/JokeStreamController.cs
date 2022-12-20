using System.Net.Sockets;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

namespace LightningTalks.JokeServer.ApiServer.Controllers;

[ApiController]
[Route("[controller]")]
public class JokeStreamController : ControllerBase
{

    private readonly ILogger<JokeStreamController> _logger;
    private string _chatHost;

    public JokeStreamController(IConfiguration configuration, ILogger<JokeStreamController> logger)
    {
        _logger = logger;
        _chatHost = configuration["ChatHost"];
    }

    [HttpGet]
    public async Task Get() {
        Response.Headers.Add("Content-Type", "text/event-stream");
        //read 128 bytes at a time from socket
        using TcpClient tcpClient = new TcpClient(_chatHost, 8989);
        NetworkStream stream = tcpClient.GetStream();

        while (stream != null)
        {
            byte[] data = new byte[128];

            Int32 bytes = stream.Read(data, 0, data.Length);


            //write out
            await Response.Body.WriteAsync(data, 0, bytes);
            await Response.Body.FlushAsync();
        }

    }
}
