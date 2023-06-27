using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace BusinessObject
{
    public partial class PlantShopContext : DbContext
    {
        public PlantShopContext()
        {
        }

        public PlantShopContext(DbContextOptions<PlantShopContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Account> Accounts { get; set; } 
        public virtual DbSet<OrderDetail> OrderDetails { get; set; } 
        public virtual DbSet<Product> Products { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                optionsBuilder.UseSqlServer("Server=(local);uid=sa;pwd=12345;database= PlantShopDB;TrustServerCertificate=True;");
            }
        }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<OrderDetail>().HasOne(x => x.Product).WithMany(x => x.OrderDetailsOrdered).HasForeignKey(x => x.ProductId);
            builder.Entity<OrderDetail>().HasOne(x => x.Account).WithMany(x => x.OrderDetails).HasForeignKey(x => x.AccountId);


            OnModelCreatingPartial(builder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
