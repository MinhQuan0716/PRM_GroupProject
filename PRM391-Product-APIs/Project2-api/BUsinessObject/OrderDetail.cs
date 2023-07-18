using System.ComponentModel.DataAnnotations.Schema;

namespace BusinessObject
{
    public class OrderDetail : BaseEntity
    {
        public int? OrderId { get; set; }
        public int? ProductId { get; set; }
        public int Amount { get; set; }
        public virtual Order? Order { get; set; }
        
        public virtual Product? Product { get; set; }
    }
}