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
                optionsBuilder.UseSqlServer("Server=(local);uid=sa;pwd=12345;database= PlantShopDB;TrustServerCertificate=True;");
            }
        }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<OrderDetail>().HasOne(x => x.Product).WithMany(x => x.OrderDetailsOrdered).HasForeignKey(x => x.ProductId);
            builder.Entity<OrderDetail>().HasOne(x => x.Account).WithMany(x => x.OrderDetails).HasForeignKey(x => x.AccountId);


            OnModelCreatingPartial(builder);
        }
        public void SeedData()
        {
            if (Accounts.Any() || Products.Any() || OrderDetails.Any())
            {
                // Data already exists, no need to seed
                return;
            }

            var accounts = CreateAccountSeedData(10);
            var products = CreateProductSeedData(10);
            var orderDetails = CreateOrderDetailSeedData(accounts, products);

            Accounts.AddRange(accounts);
            Products.AddRange(products);
            OrderDetails.AddRange(orderDetails);
            SaveChanges();
        }
        private List<Account> CreateAccountSeedData(int count)
        {
            var accounts = new List<Account>();

            for (int i = 1; i <= count; i++)
            {
                var account = new Account
                {
                    Username = $"username{i}",
                    Password = $"password{i}",
                    Fullname = $"Fullname{i}",
                    Avatar = $"Avatar{i}"
                };

                accounts.Add(account);
            }

            return accounts;
        }

        private List<Product> CreateProductSeedData(int count)
        {
            var products = new List<Product>();

            for (int i = 1; i <= count; i++)
            {
                var product = new Product
                {
                    ProductName = $"Product{i}",
                    Price = i * 100,
                    Description = $"Description{i}",
                    ImgPath = $"ImgPath{i}"
                };

                products.Add(product);
            }

            return products;
        }

        private List<OrderDetail> CreateOrderDetailSeedData(List<Account> accounts, List<Product> products)
        {
            var orderDetails = new List<OrderDetail>();

            // Assuming each account and product should have a corresponding order detail
            for (int i = 0; i < accounts.Count; i++)
            {
                var orderDetail = new OrderDetail
                {
                    AccountId = accounts[i].Id,
                    ProductId = products[i].Id,
                    Account = accounts[i],
                    Product = products[i]
                };

                orderDetails.Add(orderDetail);
            }

            return orderDetails;
        }
        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
