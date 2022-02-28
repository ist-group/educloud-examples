using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EducloudExample.Models
{
    public class ApiResponse<T>
    {
        public List<T> data { get; set; }
    }
}
