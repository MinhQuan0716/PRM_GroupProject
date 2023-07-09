using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessObject.DTOs
{
    public class OrderFormat
    {
       

        public int AccountId { get; set; }

        public int ShipFee { get; set; } = 100;

        public int TotalPrice { get; set; } = 100;

       public string Address { get; set; } = string.Empty;

        public bool isPayed { get; set; } = false;

        public List<OrderDetailDTO> Details { get; set; } 
    }
}
