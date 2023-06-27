using System.ComponentModel.DataAnnotations;

namespace BusinessObject
{
    public class Account: BaseEntity
    {
        [MaxLength(100)]
        public string Username { get; set; } = string.Empty;
        [MaxLength(100)]
        public string Password { get; set; } = string.Empty;
        [MaxLength(100)]
        public string Fullname { get; set; } = string.Empty;
        [MaxLength(5000)]
        public string? Avatar { get; set; } = string.Empty;

        public virtual ICollection<OrderDetail>? OrderDetails { get; set; } = null;

    }
}