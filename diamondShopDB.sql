USE [master]
GO
/****** Object:  Database [diamondShopDB]    Script Date: 6/16/2024 10:22:01 AM ******/
CREATE DATABASE [diamondShopDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'diamondShopDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHUCDUYEN\MSSQL\DATA\diamondShopDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'diamondShopDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHUCDUYEN\MSSQL\DATA\diamondShopDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [diamondShopDB] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [diamondShopDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [diamondShopDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [diamondShopDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [diamondShopDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [diamondShopDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [diamondShopDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [diamondShopDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [diamondShopDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [diamondShopDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [diamondShopDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [diamondShopDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [diamondShopDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [diamondShopDB] SET  ENABLE_BROKER 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [diamondShopDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [diamondShopDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [diamondShopDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [diamondShopDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [diamondShopDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [diamondShopDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [diamondShopDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [diamondShopDB] SET  MULTI_USER 
GO
ALTER DATABASE [diamondShopDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [diamondShopDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [diamondShopDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [diamondShopDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [diamondShopDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [diamondShopDB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [diamondShopDB] SET QUERY_STORE = ON
GO
ALTER DATABASE [diamondShopDB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [diamondShopDB]
GO
/****** Object:  Table [dbo].[account]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [varchar](255) NULL,
	[is_active] [bit] NULL,
	[last_name] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[roles] [varchar](255) NULL,
	[username] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[account_order]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account_order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[account_id] [int] NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NULL,
	[member_level] [varchar](255) NULL,
	[phone_number] [varchar](255) NULL,
	[point] [int] NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[date_status_order]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[date_status_order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[date_status] [datetime2](6) NULL,
	[status] [varchar](255) NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[carat_weight] [real] NULL,
	[certificate_number] [varchar](255) NULL,
	[clarity] [varchar](255) NULL,
	[color] [varchar](255) NULL,
	[cut] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[origin] [varchar](255) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[status] [bit] NULL,
	[account_id] [int] NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond_shell]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond_shell](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[gender] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[material] [varchar](255) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[secondary_stone_type] [varchar](255) NULL,
	[status] [bit] NULL,
	[account_id] [int] NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[customer_name] [varchar](255) NULL,
	[is_cancel] [bit] NOT NULL,
	[number_phone] [varchar](255) NULL,
	[total_price] [float] NOT NULL,
	[warranty_end_date] [datetime2](6) NULL,
	[warranty_start_date] [datetime2](6) NULL,
	[customer_id] [int] NULL,
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[size] [int] NULL,
	[diamond_id] [int] NULL,
	[diamond_shell_id] [int] NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NULL,
	[discount_percent] [real] NULL,
	[end_date] [datetime2](6) NULL,
	[member_level_promotion] [varchar](255) NULL,
	[promotion_name] [varchar](255) NULL,
	[start_date] [datetime2](6) NULL,
	[type] [varchar](255) NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion_diamond]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion_diamond](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_id] [int] NULL,
	[promotion_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion_diamond_shell]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion_diamond_shell](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_shell_id] [int] NULL,
	[promotion_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[size] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size_diamond_shell]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size_diamond_shell](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_shell_id] [int] NULL,
	[size_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[status_order]    Script Date: 6/16/2024 10:22:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[status_order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[status_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[account] ON 

INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (1, N'Tien', 1, N'Bui', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'MANAGER', N'tienbh')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (2, N'Duyen', 1, N'Nguyen', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'ADMIN', N'duyenntp')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (3, N'Khoa', 1, N'Tran', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'SALE_STAFF', N'khoatnn')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (4, N'Dat', 1, N'Ngo', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'DELIVERY_STAFF', N'datnd')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (5, N'Huong', 1, N'Nguyen', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'CUSTOMER', N'0773898293')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (6, N'Quynh', 1, N'Pham', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'CUSTOMER', N'0988546643')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (7, N'Chi', 1, N'Ly', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'CUSTOMER', N'0924512422')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (8, N'Trang', 1, N'Nguyen', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'CUSTOMER', N'0981893407')
INSERT [dbo].[account] ([id], [first_name], [is_active], [last_name], [password], [roles], [username]) VALUES (9, N'Tu', 1, N'Nguyen', N'$2a$10$tmsU5HMiFAhN8P6eyxA5Zu9E/g1uTCYK.6bIWc6J5ccoWSAO/cmP2', N'CUSTOMER', N'0988642532')
SET IDENTITY_INSERT [dbo].[account] OFF
GO
SET IDENTITY_INSERT [dbo].[customer] ON 

INSERT [dbo].[customer] ([id], [email], [member_level], [phone_number], [point], [account_id]) VALUES (1, N'phamquynh2003qs@gmail.com', N'SILVER', N'0988546643', 0, 6)
INSERT [dbo].[customer] ([id], [email], [member_level], [phone_number], [point], [account_id]) VALUES (2, N'lykimchi@gmail.com', N'SILVER', N'0924512422', 0, 7)
INSERT [dbo].[customer] ([id], [email], [member_level], [phone_number], [point], [account_id]) VALUES (3, N'nguyenthitrang@gmail.com', N'SILVER', N'0981893407', 0, 8)
INSERT [dbo].[customer] ([id], [email], [member_level], [phone_number], [point], [account_id]) VALUES (4, N'nguyentramthientu@gmail.com', N'SILVER', N'0988642532', 0, 9)
SET IDENTITY_INSERT [dbo].[customer] OFF
GO
SET IDENTITY_INSERT [dbo].[date_status_order] ON 

INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (1, CAST(N'2024-06-08T14:52:44.3000000' AS DateTime2), N'Pending', 1)
INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (2, CAST(N'2024-06-08T14:55:13.1420000' AS DateTime2), N'Pending', 2)
INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (3, CAST(N'2024-06-08T14:57:35.0700000' AS DateTime2), N'Pending', 3)
INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (4, CAST(N'2024-06-08T14:59:47.4310000' AS DateTime2), N'Pending', 4)
INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (5, CAST(N'2024-06-08T15:00:38.1170000' AS DateTime2), N'Cancel', 3)
INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (6, CAST(N'2024-06-08T15:01:21.8780000' AS DateTime2), N'Confirmed', 1)
INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (7, CAST(N'2024-06-15T13:20:07.4470000' AS DateTime2), N'Confirmed', 2)
INSERT [dbo].[date_status_order] ([id], [date_status], [status], [order_id]) VALUES (8, CAST(N'2024-06-15T13:21:06.8260000' AS DateTime2), N'Delivering', 1)
SET IDENTITY_INSERT [dbo].[date_status_order] OFF
GO
SET IDENTITY_INSERT [dbo].[diamond] ON 

INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (1, 3.3, N'GIA1234567890', N'VS2', N'G', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (HPHT)', 11045000, 1000, 1, NULL, N'Diamond 3.3 VS2 G EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (2, 3.6, N'GIA9876543210', N'VS1', N'G', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (HPHT)', 11421000, 1000, 1, NULL, N'Diamond 3.6 VS1 G EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (3, 3.6, N'GIA1230984567', N'VS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (CVD)', 11891000, 1500, 1, NULL, N'Diamond 3.6 VS2 F EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (4, 3.6, N'GIA4567891230', N'VS1', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (CVD)', 12267000, 1498, 1, NULL, N'Diamond 3.6 VS1 F EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (5, 3.6, N'GIA5432109876', N'VS2', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (CVD)', 12267000, 1300, 1, NULL, N'Diamond 3.6 VS2 E EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (6, 3.6, N'GIA1122334455', N'VS1', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (CVD)', 12643000, 1300, 1, NULL, N'Diamond 3.6 VS1 E EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (7, 3.6, N'GIA9988776655', N'VS1', N'D', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (CVD)', 12643000, 1400, 1, NULL, N'Diamond 3.6 VS1 D EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (8, 3.6, N'GIA6677889900', N'VVS1', N'H', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (CVD)', 12737000, 1249, 1, NULL, N'Diamond 3.6 VVS1 H EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (9, 3.6, N'GIA2233445566', N'IF', N'D', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (HPHT)', 18095000, 1050, 1, NULL, N'Diamond 3.6 IF D EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (10, 3.6, N'GIA5544332211', N'IF', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642', N'Artificial diamonds (HPHT)', 17155000, 1249, 1, NULL, N'Diamond 3.6 IF F EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (11, 4.1, N'GIA4433221100', N'VS2', N'F', N'VG', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (HPHT)', 17672000, 1643, 1, NULL, N'Diamond 4.1 VS2 F VG Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (12, 4.1, N'GIA5566778899', N'VS1', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (HPHT)', 18659000, 1250, 1, NULL, N'Diamond 4.1 VS1 F EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (13, 4.1, N'GIA1122446688', N'VVS1', N'H', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 18894000, 1250, 1, NULL, N'Diamond 4.1 VVS1 H EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (14, 4.1, N'GIA9988112233', N'VS2', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (HPHT)', 18659000, 1149, 1, NULL, N'Diamond 4.1 VS2 E EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (15, 4.1, N'GIA3344556677', N'VVS1', N'G', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (HPHT)', 19646000, 1249, 1, NULL, N'Diamond 4.1 VVS1 G EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (16, 4.1, N'GIA1001101201', N'VS1', N'D', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 20116000, 1000, 1, NULL, N'Diamond 4.1 VS1 D EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (17, 4.1, N'GIA1002102302', N'VVS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 21432000, 1050, 1, NULL, N'Diamond 4.1 VVS2 F EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (18, 4.5, N'GIA1003103403', N'VS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 21582000, 1250, 1, NULL, N'Diamond 4.5 VS2 F EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (19, 4.5, N'GIA1003103403', N'VS1', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (HPHT)', 22438000, 1330, 1, NULL, N'Diamond 4.5 VS1 F EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (20, 4.3, N'GIA1003103403', N'VS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (HPHT)', 23594000, 1425, 1, NULL, N'Diamond 4.3 VS2 F EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (21, 5.4, N'GIA1004104504', N'VS2', N'H', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (CVD)', 66129000, 965, 1, NULL, N'Diamond 5.4 VS2 H EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (22, 5.4, N'GIA1005105605', N'VS1', N'H', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (HPHT)', 67116000, 800, 1, NULL, N'Diamond 5.4 VS1 H EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (23, 5.4, N'GIA1006106706', N'VVS2', N'H', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (CVD)', 71487000, 965, 1, NULL, N'Diamond 5.4 VVS2 H EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (24, 5.4, N'GIA1007107807', N'VVS1', N'H', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (HPHT)', 73273000, 1000, 1, NULL, N'Diamond 5.4 VVS1 H EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (25, 5.4, N'GIA1008108908', N'VVS1', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (CVD)', 83991000, 766, 1, NULL, N'Diamond 5.4 VVS1 F EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (26, 5.4, N'GIA1009110010', N'IF', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (HTTP)', 98333000, 643, 1, NULL, N'Diamond 5.4 IF F EX Artificial diamonds (HTTP)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (27, 5.4, N'GIA1010111121', N'IF', N'D', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (CVD)', 116038000, 542, 1, NULL, N'Diamond 5.4 IF D EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (28, 5.4, N'GIA1011112232', N'IF', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (HPHT)', 107037000, 500, 1, NULL, N'Diamond 5.4 IF E EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (29, 5.4, N'GIA1012113343', N'VVS1', N'D', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (HPHT)', 103519000, 340, 1, NULL, N'Diamond 5.4 VVS1 D EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (30, 5.4, N'GIA1013114454', N'VS2', N'D', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a', N'Artificial diamonds (HPHT)', 95051000, 400, 1, NULL, N'Diamond 5.4 VS2 D EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (31, 6, N'GIA1014115565', N'VS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0bd8e181-345f-4b53-92d4-1c0ce9386df1-Diamond6.0lyFrontalOrigin.png?alt=media&token=b59fb150-6770-4376-96cc-3d3acda61602', N'Artificial diamonds (HPHT)', 144446000, 100, 1, NULL, N'Diamond 6 VS2 F EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (32, 6, N'GIA1015116676', N'VS1', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0bd8e181-345f-4b53-92d4-1c0ce9386df1-Diamond6.0lyFrontalOrigin.png?alt=media&token=b59fb150-6770-4376-96cc-3d3acda61602', N'Artificial diamonds (CVD)', 157821000, 110, 1, NULL, N'Diamond 6 VS1 E EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (33, 6, N'GIA1015116676', N'VVS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0bd8e181-345f-4b53-92d4-1c0ce9386df1-Diamond6.0lyFrontalOrigin.png?alt=media&token=b59fb150-6770-4376-96cc-3d3acda61602', N'Artificial diamonds (HPHT)', 195455000, 100, 1, NULL, N'Diamond 6 VVS2 F EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (34, 6.6, N'GIA1016117787', N'SI1', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 282517000, 100, 1, NULL, N'Diamond 6.6 SI1 F EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (35, 6.3, N'GIA1017118898', N'VVS2', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 288686000, 100, 1, NULL, N'Diamond 6.3 VVS2 E EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (36, 6.3, N'GIA1018120010', N'VS2', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 307105000, 100, 1, NULL, N'Diamond 6.3 VS2 E EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (37, 7.2, N'GIA1019121121', N'VVS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (HPHT)', 609079000, 150, 1, NULL, N'Diamond 7.2 VVS2 F EX Artificial diamonds (HPHT)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (38, 8.1, N'GIA1020122232', N'VS2', N'F', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 1183830000, 100, 1, NULL, N'Diamond 8.1 VS2 F EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (39, 8.1, N'GIA1021123343', N'VVS2', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 1468621000, 100, 1, NULL, N'Diamond 8.1 VVS2 E EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (40, 9, N'GIA1022124454', N'VS2', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 1995861000, 120, 1, NULL, N'Diamond 9 VS2 E EX Artificial diamonds (CVD)')
INSERT [dbo].[diamond] ([id], [carat_weight], [certificate_number], [clarity], [color], [cut], [image], [origin], [price], [quantity], [status], [account_id], [name]) VALUES (41, 9, N'GIA1022124444', N'VS1', N'E', N'EX', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384', N'Artificial diamonds (CVD)', 2287938000, 50, 1, NULL, N'Diamond 9.0 VS1 E EX Artificial diamonds (CVD)')
SET IDENTITY_INSERT [dbo].[diamond] OFF
GO
SET IDENTITY_INSERT [dbo].[diamond_shell] ON 

INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (1, N'female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_10dfff5f0391472bb62d9199070f6867_master_female.png?alt=media&token=d8ea39c7-b26d-4365-854d-2495d1725852', N'Platinum 14K', 12079000, 96, N'KC DIA WHIRD1.0x18', 1, NULL, N'Diamond Shell female Platinum 14K KC DIA WHIRD1.0x18')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (2, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_4f231b0d662c4e05966bbb22c52b809b_master_female.png?alt=media&token=b11643a3-41f1-4ac9-b596-3c49c83bab09', N'Platinum 14K', 16967000, 109, N'KC DIA WHIRD1.5x2, 1.2x18', 1, NULL, N'Diamond Shell Female Platinum 14K KC DIA WHIRD1.5x2, 1.2x18')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (3, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_d072d7d72b884e24b211fc8025cc23e2_master_female.png?alt=media&token=f86acca2-f64c-40d0-87fc-c6f638229726', N'Platinum 14K', 38258000, 120, N'KC DIA WHIRD1.6x2, 1.5x8, 1.3x', 1, NULL, N'Diamond Shell Female Platinum 14K KC DIA WHIRD1.6x2, 1.5x8, 1.3x')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (4, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_1fe465f2203b4f798097595fb3a80596_master_female.png?alt=media&token=b1f97aee-ac62-4cb3-af7f-95ba24adc516', N'Platinum 14K', 19881000, 120, N'KC DIA WHIRD1.5x6, 1.2x16', 1, NULL, N'Diamond Shell Female Platinum 14K KC DIA WHIRD1.5x6, 1.2x16')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (5, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_dac95543763b413e86ba74f758f7ffe8_master_female.png?alt=media&token=e6076655-c336-48d0-a7a3-ad0eeb6de03a', N'Platinum 18K', 12899000, 100, N'KC DIA WHIRD0.9x10', 1, NULL, N'Diamond Shell Female Platinum 18K KC DIA WHIRD0.9x10')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (6, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnxmxmy010578-nhan-vang-18k-dinh-da-ecz-pnj-1-female.png?alt=media&token=1d0f4469-416d-49cc-a976-a07363f51399', N'Gold 18K', 6359000, 100, N'Stone ECZ PNJ XMXMY010578', 1, NULL, N'Diamond Shell Female Gold 18K Stone ECZ PNJ XMXMY010578')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (7, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnxmxmy010577-nhan-vang-18k-dinh-da-ecz-pnj-01-female.png?alt=media&token=4e67b3a7-8992-48ba-86df-c1e67853ac3f', N'Gold 18K', 12899000, 100, N'Stone ECZ PNJ XMXMY010577', 1, NULL, N'Diamond Shell Female Gold 18K Stone ECZ PNJ XMXMY010577')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (8, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnddddy060011-nhan-kim-cuong-vang-14k-pnj-1-female.png?alt=media&token=62b199da-51c5-4168-9d20-c9d9d52d97c2', N'Gold 14K', 13380000, 100, N'Gold 14K PNJ DDDDY060011', 1, NULL, N'Diamond Shell Female Gold 14K Gold 14K PNJ DDDDY060011')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (9, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnddddc001755-nhan-kim-cuong-vang-14k-disney-pnj-cinderella-1-female.png?alt=media&token=1a215f32-09f6-49bf-9ef1-ae64c99c253b', N'Platinum 18K', 12899000, 100, N'Gold 14K PNJ Cinderella DDDDC001755', 1, NULL, N'Diamond Shell Female Platinum 18K Gold 14K PNJ Cinderella DDDDC001755')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (10, N'Female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_c8ce7216a2c2468ea0ebfa622590ad83_master-female.png?alt=media&token=acc94d4e-a80d-4f03-bf22-04aa32f32c31', N'Platinum 14K', 48410000, 100, N'KC DIA WHIRD1.8x10,1.3x20,0', 1, NULL, N'Diamond Shell Female Platinum 14K KC DIA WHIRD1.8x10,1.3x20,0')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (11, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_dc0613b6df0648bbbd3e44ca3dee6330_master-male.png?alt=media&token=801f1c91-5c6f-4c2b-b461-10b83670fa38', N'Platinum 950 and Gold 18K', 69983000, 200, N'KC DIA WHIRD1.3x20', 1, NULL, N'Diamond Shell Male Platinum 950 and Gold 18K KC DIA WHIRD1.3x20')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (12, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_0c3f9713ad174f46b8a6ce2a163a3642_master-male.png?alt=media&token=0827b7d4-0076-45da-a935-f31dfb8a9e66', N'Platinum 10K', 19881000, 160, N'KC DIA WHIRD1.3x8', 1, NULL, N'Diamond Shell Male Platinum 10K KC DIA WHIRD1.3x8')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (13, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_ed9608851a5d433da0d9796e5da061f5_master-male.png?alt=media&token=fc9a25f0-e829-477c-8fe0-48eccb4ba428', N'Platinum 14K', 79806000, 140, N'KC DIA WHIRD1.3x72', 1, NULL, N'Diamond Shell Male Platinum 14K KC DIA WHIRD1.3x72')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (14, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_f3d51660903e4d04a4d3ca31ea1be2eb_master-male.png?alt=media&token=1ba46b87-330b-4e0a-a0ba-88ffd6747338', N'Platinum 14K', 48410000, 100, N'KC DIA WHIRD1.8x10,1.3x20,0', 1, NULL, N'Diamond Shell Male Platinum 14K KC DIA WHIRD1.8x10,1.3x20,0')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (15, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_b1644158e3db49ad8b804f4899152764_master-male.png?alt=media&token=8fdc5f25-3e8d-4f52-83bb-c40fc419e39b', N'Platinum 14K', 38211000, 132, N'KC DIA WHIRD1.2x14', 1, NULL, N'Diamond Shell Male Platinum 14K KC DIA WHIRD1.2x14')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (16, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_74f3f5fcbf2b43bcb17cbe0c9833154c_master-male.png?alt=media&token=0a75d1c3-2bd4-4594-bad2-bc8a7c1511f0', N'Platinum 14K and Gold', 33229000, 200, N'KC DIA WHIRD1.0x20', 1, NULL, N'Diamond Shell Male Platinum 14K and Gold KC DIA WHIRD1.0x20')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (17, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_598b9fbda10d4536a466e781d9a5292e_master-male.png?alt=media&token=51bb409d-aab0-4b8b-9575-164c1fe1a93f', N'Gold 14K', 48551000, 230, N'KC DIA WHIRD0.8x58', 1, NULL, N'Diamond Shell Male Gold 14K KC DIA WHIRD0.8x58')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (18, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_9fd746469d1e4e0cac4d52cc3df344d0_master-male.png?alt=media&token=38451227-a04c-4aa3-8798-2e1453504ea6', N'Platinum 14K and Gold', 64296000, 300, N'KC DIA WHIRD1.5x22, 1.3x4, 1.2', 1, NULL, N'Diamond Shell Male Platinum 14K and Gold KC DIA WHIRD1.5x22, 1.3x4, 1.2')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (19, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_e0825719c40047c28a8852510871dcdc_master-male.png?alt=media&token=593bb72f-6681-4ec3-ab8c-828dc2475f86', N'Gold 14K', 27542000, 340, N'KC DIA WHIRD1.3x8', 1, NULL, N'Diamond Shell Male Gold 14K KC DIA WHIRD1.3x8')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (20, N'Male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_6bca1e0868dd40ec90a96a308715b7db_master.png?alt=media&token=3f89177b-c275-4b38-bcd5-a7adf1d37f58', N'Platinum 14K', 49538000, 160, N'KC DIA WHIRD1.2x20, 1.1x8, 1.0', 1, NULL, N'Diamond Shell Male Platinum 14K KC DIA WHIRD1.2x20, 1.1x8, 1.0')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (21, N'female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_ac7ef2be81c24d4080ade8c218e14a77_master.png?alt=media&token=0e02650b-6886-4eac-a4c3-344b8230d34c', N'Platinum 14K', 58938000, 120, N'KC DIA WHIRD3.0x2,0.9x116', 0, NULL, N'Diamond Shell female Platinum 14K KC DIA WHIRD3.0x2,0.9x116')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (22, N'female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_ac7ef2be81c24d4080ade8c218e14a77_master.png?alt=media&token=0e02650b-6886-4eac-a4c3-344b8230d34c', N'Platinum 14K', 58938000, 120, N'KC DIA WHIRD3.0x2,0.9x116', 1, NULL, N'Diamond Shell female Platinum 14K KC DIA WHIRD3.0x2,0.9x116')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (23, N'female', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_ac7ef2be81c24d4080ade8c218e14a77_master.png?alt=media&token=0e02650b-6886-4eac-a4c3-344b8230d34c', N'Platinum 14K', 58938000, 120, N'KC DIA WHIRD3.0x2,0.9x116', 1, NULL, N'Diamond Shell female Platinum 14K KC DIA WHIRD3.0x2,0.9x116')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (24, N'male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_f9e4da67379a46f6b4bd05b69c3f63fa_master_male.png?alt=media&token=d18b27e4-6967-43ee-9fc6-3275e2e40e93', N'Platinum 14K', 64860000, 130, N'KC DIA WHIRD1.6x16', 1, NULL, N'Diamond Shell male Platinum 14K KC DIA WHIRD1.6x16')
INSERT [dbo].[diamond_shell] ([id], [gender], [image], [material], [price], [quantity], [secondary_stone_type], [status], [account_id], [name]) VALUES (25, N'male', N'https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_0d610cd1aa1141d088e59fe1852d4733_master_Male.png?alt=media&token=07df043b-01a1-4788-a415-0c54bc9aa404', N'string', 58186000, 145, N'KC DIA WHIRD1.3x44', 1, NULL, N'Diamond Shell male string KC DIA WHIRD1.3x44')
SET IDENTITY_INSERT [dbo].[diamond_shell] OFF
GO
SET IDENTITY_INSERT [dbo].[order] ON 

INSERT [dbo].[order] ([id], [address], [customer_name], [is_cancel], [number_phone], [total_price], [warranty_end_date], [warranty_start_date], [customer_id], [status_id]) VALUES (1, N'Hooc Mon', N'Nhu Quynh', 0, N'0988546643', 53580000, NULL, NULL, 1, 3)
INSERT [dbo].[order] ([id], [address], [customer_name], [is_cancel], [number_phone], [total_price], [warranty_end_date], [warranty_start_date], [customer_id], [status_id]) VALUES (2, N'Long Xuyen', N'Kim Chi', 0, N'0924512422', 60959000, NULL, NULL, 2, 2)
INSERT [dbo].[order] ([id], [address], [customer_name], [is_cancel], [number_phone], [total_price], [warranty_end_date], [warranty_start_date], [customer_id], [status_id]) VALUES (3, N'Bien Hoa', N'Thi Trang', 1, N'0981893407', 12079000, NULL, NULL, 3, 5)
INSERT [dbo].[order] ([id], [address], [customer_name], [is_cancel], [number_phone], [total_price], [warranty_end_date], [warranty_start_date], [customer_id], [status_id]) VALUES (4, N'Tri Ton', N'Thien Tu', 0, N'0988642532', 31396000, NULL, NULL, 4, 1)
SET IDENTITY_INSERT [dbo].[order] OFF
GO
SET IDENTITY_INSERT [dbo].[order_detail] ON 

INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (1, 12267000, 2, 0, 4, NULL, 1)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (2, 12079000, 1, 1, NULL, 1, 1)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (3, 16967000, 1, 1, NULL, 2, 1)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (4, 17155000, 1, 0, 10, NULL, 2)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (5, 19646000, 1, 0, 15, NULL, 2)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (6, 12079000, 2, 4, NULL, 1, 2)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (7, 12079000, 1, 3, NULL, 1, 3)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (8, 12737000, 1, 0, 8, NULL, 4)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [size], [diamond_id], [diamond_shell_id], [order_id]) VALUES (9, 18659000, 1, 0, 14, NULL, 4)
SET IDENTITY_INSERT [dbo].[order_detail] OFF
GO
SET IDENTITY_INSERT [dbo].[size] ON 

INSERT [dbo].[size] ([id], [size]) VALUES (1, 13)
INSERT [dbo].[size] ([id], [size]) VALUES (2, 14)
INSERT [dbo].[size] ([id], [size]) VALUES (3, 15)
INSERT [dbo].[size] ([id], [size]) VALUES (4, 16)
INSERT [dbo].[size] ([id], [size]) VALUES (5, 17)
INSERT [dbo].[size] ([id], [size]) VALUES (6, 18)
INSERT [dbo].[size] ([id], [size]) VALUES (7, 19)
INSERT [dbo].[size] ([id], [size]) VALUES (8, 20)
SET IDENTITY_INSERT [dbo].[size] OFF
GO
SET IDENTITY_INSERT [dbo].[size_diamond_shell] ON 

INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (1, 1, 1)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (2, 1, 2)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (3, 1, 3)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (4, 1, 4)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (5, 1, 5)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (6, 1, 6)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (7, 1, 7)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (8, 1, 8)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (9, 2, 1)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (10, 2, 2)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (11, 2, 3)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (12, 2, 4)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (13, 2, 5)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (14, 2, 6)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (15, 2, 7)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (16, 2, 8)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (19, 23, 1)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (20, 23, 2)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (21, 23, 3)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (22, 23, 4)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (23, 23, 5)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (24, 24, 1)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (25, 24, 2)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (26, 24, 3)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (27, 24, 4)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (28, 24, 5)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (29, 24, 6)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (30, 24, 7)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (31, 25, 1)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (32, 25, 2)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (33, 25, 3)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (34, 25, 4)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (35, 25, 5)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (36, 25, 6)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (37, 25, 7)
INSERT [dbo].[size_diamond_shell] ([id], [diamond_shell_id], [size_id]) VALUES (38, 25, 8)
SET IDENTITY_INSERT [dbo].[size_diamond_shell] OFF
GO
SET IDENTITY_INSERT [dbo].[status_order] ON 

INSERT [dbo].[status_order] ([id], [status_name]) VALUES (1, N'Pending')
INSERT [dbo].[status_order] ([id], [status_name]) VALUES (2, N'Confirmed')
INSERT [dbo].[status_order] ([id], [status_name]) VALUES (3, N'Delivering')
INSERT [dbo].[status_order] ([id], [status_name]) VALUES (4, N'Delivered')
INSERT [dbo].[status_order] ([id], [status_name]) VALUES (5, N'Cancel')
SET IDENTITY_INSERT [dbo].[status_order] OFF
GO
/****** Object:  Index [UK_jwt2qo9oj3wd7ribjkymryp8s]    Script Date: 6/16/2024 10:22:02 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_jwt2qo9oj3wd7ribjkymryp8s] ON [dbo].[customer]
(
	[account_id] ASC
)
WHERE ([account_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account_order]  WITH CHECK ADD  CONSTRAINT [FKnjytchwkjqaoy3vitj6ihpdro] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([id])
GO
ALTER TABLE [dbo].[account_order] CHECK CONSTRAINT [FKnjytchwkjqaoy3vitj6ihpdro]
GO
ALTER TABLE [dbo].[account_order]  WITH CHECK ADD  CONSTRAINT [FKr4qmwxhwxsbvgg1128gajovux] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[account_order] CHECK CONSTRAINT [FKr4qmwxhwxsbvgg1128gajovux]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [FKrhuwpv28b7r1xttdimd6uxgq8] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [FKrhuwpv28b7r1xttdimd6uxgq8]
GO
ALTER TABLE [dbo].[date_status_order]  WITH CHECK ADD  CONSTRAINT [FKgdiwgqo47c8je3es052w9v0k7] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([id])
GO
ALTER TABLE [dbo].[date_status_order] CHECK CONSTRAINT [FKgdiwgqo47c8je3es052w9v0k7]
GO
ALTER TABLE [dbo].[diamond]  WITH CHECK ADD  CONSTRAINT [FK18ox4xfix4ylqr8fpwbsjystg] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[diamond] CHECK CONSTRAINT [FK18ox4xfix4ylqr8fpwbsjystg]
GO
ALTER TABLE [dbo].[diamond_shell]  WITH CHECK ADD  CONSTRAINT [FK5ur9vvhbu92723iswoxgh9hi3] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[diamond_shell] CHECK CONSTRAINT [FK5ur9vvhbu92723iswoxgh9hi3]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK7vschkjn6ss3p1ttrt22asq6g] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK7vschkjn6ss3p1ttrt22asq6g]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FKm66neelxkjwxq0knlq39wbpph] FOREIGN KEY([status_id])
REFERENCES [dbo].[status_order] ([id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FKm66neelxkjwxq0knlq39wbpph]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKlsonu3svt3kmhylu8xt7cymcq] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKlsonu3svt3kmhylu8xt7cymcq]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKm9u9g1ywmwgx17wfd29tqc8a5] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKm9u9g1ywmwgx17wfd29tqc8a5]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKrnjq2huvu2qafdqh75ae7g247] FOREIGN KEY([diamond_id])
REFERENCES [dbo].[diamond] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKrnjq2huvu2qafdqh75ae7g247]
GO
ALTER TABLE [dbo].[promotion]  WITH CHECK ADD  CONSTRAINT [FK969ko09pwsnt6k5c6koecm4sa] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[promotion] CHECK CONSTRAINT [FK969ko09pwsnt6k5c6koecm4sa]
GO
ALTER TABLE [dbo].[promotion_diamond]  WITH CHECK ADD  CONSTRAINT [FK2bnsijtpa7iho8fr27bspjwgi] FOREIGN KEY([diamond_id])
REFERENCES [dbo].[diamond] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond] CHECK CONSTRAINT [FK2bnsijtpa7iho8fr27bspjwgi]
GO
ALTER TABLE [dbo].[promotion_diamond]  WITH CHECK ADD  CONSTRAINT [FK6vq0rio0lu42vfih8pxeaglc4] FOREIGN KEY([promotion_id])
REFERENCES [dbo].[promotion] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond] CHECK CONSTRAINT [FK6vq0rio0lu42vfih8pxeaglc4]
GO
ALTER TABLE [dbo].[promotion_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKgj8gvyh6098q0co6eekors89g] FOREIGN KEY([promotion_id])
REFERENCES [dbo].[promotion] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond_shell] CHECK CONSTRAINT [FKgj8gvyh6098q0co6eekors89g]
GO
ALTER TABLE [dbo].[promotion_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKpiyotti1rpfmpwyg3jxp3jhp0] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond_shell] CHECK CONSTRAINT [FKpiyotti1rpfmpwyg3jxp3jhp0]
GO
ALTER TABLE [dbo].[size_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FK37am41nybrg106felrrjsmbae] FOREIGN KEY([size_id])
REFERENCES [dbo].[size] ([id])
GO
ALTER TABLE [dbo].[size_diamond_shell] CHECK CONSTRAINT [FK37am41nybrg106felrrjsmbae]
GO
ALTER TABLE [dbo].[size_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKnf89brofc668pe6oe2w9uirrp] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([id])
GO
ALTER TABLE [dbo].[size_diamond_shell] CHECK CONSTRAINT [FKnf89brofc668pe6oe2w9uirrp]
GO
ALTER TABLE [dbo].[size]  WITH CHECK ADD CHECK  (([size]>=(1) AND [size]<=(32)))
GO
USE [master]
GO
ALTER DATABASE [diamondShopDB] SET  READ_WRITE 
GO
