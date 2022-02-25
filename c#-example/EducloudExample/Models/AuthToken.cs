using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EducloudExample.Models
{
    public class AuthToken
    {
        public string access_token { get; set; }
        private int _expires_in_seconds;
        public int expires_in
        {
            get { return _expires_in_seconds; }
            set
            {
                expires_at = DateTime.Now.AddSeconds(value);
                _expires_in_seconds = value;
            }
        }
        public DateTime expires_at { get; set; }
        public string token_type { get; set; }
        public string scope { get; set; }

    }
}
