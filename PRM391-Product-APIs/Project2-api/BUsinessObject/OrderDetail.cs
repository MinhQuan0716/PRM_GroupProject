namespace BusinessObject
{
    public class OrderDetail : BaseEntity
    {
        public int? AccountId { get; set; }
        public int? ProductId { get; set; }
        public virtual Account? Account { get; set; }
        public virtual Product? Product { get; set; }

    }
}