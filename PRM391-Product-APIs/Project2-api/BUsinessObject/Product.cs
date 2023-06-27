using System.ComponentModel.DataAnnotations;

namespace BusinessObject
{
    public class Product: BaseEntity
    {
        [MaxLength(256)]
        public string? ProductName { get; set; } = string.Empty;
        [Required]
        public int Price { get; set; }
        [MaxLength(5000)]
        public string? Description { get; set; } = string.Empty;
        public string? ImgPath { get; set; } = null;

        public virtual ICollection<OrderDetail> OrderDetailsOrdered { get; set; } = new List<OrderDetail>(); 
        
    }
}