using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace BusinessObject
{
	public class Order:BaseEntity
	{
		public int TotalPrice { get; set; }
		public int ShipFee { get; set; }
		public string Address { get; set; } = null;
		public int? AccountId { get; set; }	
		public bool isPayed { get; set; }


		public virtual Account? Account { get; set; } = default;
	
		public virtual ICollection<OrderDetail>? OrderDetails { get; set; } = default;
	}
}
