using System.ComponentModel.DataAnnotations.Schema;

namespace BusinessObject
{
    public class BaseEntity
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
    }
}