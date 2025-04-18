-- use master

-- create database TrainTicketBookingSystem

-- use TrainTicketBookingSystem

-- Bảng nhân viên
CREATE SEQUENCE EmployeeSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Employee (
    EmployeeID CHAR(12) PRIMARY KEY DEFAULT ('Emp' + RIGHT('000000000' + CAST(NEXT VALUE FOR EmployeeSequence AS VARCHAR(9)), 9)),
    FullName NVARCHAR(100) NOT NULL,     -- Supports Unicode characters (for names)
    Gender BIT NOT NULL,                 -- Boolean in Java, BIT in SQL (1 = Male, 0 = Female)
    DateOfBirth DATE NOT NULL,           -- LocalDate in Java, DATE in SQL
    Email VARCHAR(100) NOT NULL UNIQUE,  -- Email address with uniqueness constraint
    PhoneNumber VARCHAR(20) NULL,        -- Phone number is optional
    Role NVARCHAR(50) NOT NULL,          -- Role stored as a string
    StartingDate DATE NOT NULL,          -- Starting date of employment
    ImageSource VARCHAR(255) NULL        -- Path to the image, optional
);
INSERT INTO Employee (FullName, Gender, DateOfBirth, Email, PhoneNumber, Role, StartingDate, ImageSource) 
VALUES (N'Pham Đức Tài', 1, '2003-10-27', 'phamductai102703@gmai.com', '0846107843', N'Quản Lý', '2024-01-05', 'profile.png');
go

-- Bảng tài khoản
CREATE TABLE Account (
    Username NVARCHAR(50) NOT NULL PRIMARY KEY,    -- Username with uniqueness constraint
    Password NVARCHAR(255) NOT NULL,          -- Password (hashed for security)
    EmployeeID char(12) NOT NULL,          -- Foreign key referencing EmployeeID
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)  -- Foreign key constraint
);
insert into Account(Username, Password, EmployeeID)
values ('admin', '$2a$12$a2WIAYaqQ8A0/5WhuIO0GO6WHCNG33sH1xjd5uZwd6MllWazZC5Mm','Emp000000001')
go

-- Bảng Thuế
CREATE SEQUENCE TaxSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Tax (
    TaxID CHAR(6) PRIMARY KEY DEFAULT ('Tax' + RIGHT('000' + CAST(NEXT VALUE FOR TaxSequence AS VARCHAR(3)), 3)),
    TaxName NVARCHAR(100) NOT NULL,           -- Tên của loại thuế (VAT, GST, v.v.)
    TaxRate DECIMAL(5,2) NOT NULL,            -- Tỷ lệ thuế (ví dụ: 10.00 cho 10%)
    Status BIT NOT NULL
);
INSERT INTO Tax (TaxName, TaxRate, Status)
VALUES
    (N'Thuế giá trị gia tăng (VAT)', 10.00, 1),             -- Thuế giá trị gia tăng (VAT) với tỷ lệ 10%
    (N'Thuế hàng hóa và dịch vụ (GST)', 5.00, 1),          -- Thuế hàng hóa và dịch vụ (GST) với tỷ lệ 5%
    (N'Thuế thu nhập doanh nghiệp (CIT)', 20.00, 1);        -- Thuế thu nhập doanh nghiệp (CIT) với tỷ lệ 20%
go

-- Bảng khuyến mãi
CREATE SEQUENCE PromotionSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Promotion (
    PromotionID CHAR(6) PRIMARY KEY DEFAULT ('Pro' + RIGHT('000' + CAST(NEXT VALUE FOR PromotionSequence AS VARCHAR(3)), 3)),
    Description NVARCHAR(255) NULL,              -- Mô tả khuyến mãi
    DiscountPercent DECIMAL(5,2) NOT NULL,       -- Phần trăm giảm giá (VD: 10.00 cho 10%)
	Status BIT NOT NULL DEFAULT 1,             -- Trạng thái kích hoạt khuyến mãi (1: đang hoạt động, 0: không hoạt động)
    StartDate DATE NOT NULL,                     -- Ngày bắt đầu áp dụng khuyến mãi
    EndDate DATE                        -- Ngày kết thúc khuyến mãi
);
INSERT INTO Promotion (Description, DiscountPercent, Status, StartDate, EndDate)
VALUES
    (N'Khuyến mãi giảm 15% cho vé khứ hồi', 15.00, 1, '2024-10-01', null),
    (N'Khuyến mãi 10% cho vé cuối tuần', 10.00, 1, '2024-10-15', '2024-10-31');
go

-- Bảng dịch vụ
CREATE SEQUENCE ServiceSequence
    START WITH 1
    INCREMENT BY 1;
go
create table Service (
    ServiceID CHAR(12) PRIMARY KEY DEFAULT ('Ser' + RIGHT('000000000' + CAST(NEXT VALUE FOR ServiceSequence AS VARCHAR(9)), 9)),
	ServiceName nvarchar(100) not null,
	Price money not null,
	Type nvarchar(10) not null,
	ImageSource varchar(100),
	CONSTRAINT chk_ServiceType CHECK (Type IN (N'Đồ ăn', N'Đồ uống'))
);


INSERT INTO Service (ServiceName, Price, Type, ImageSource)
VALUES 
(N'Trà', 95000, N'Đồ uống', N'images/tea.png'),
(N'Nước cam', 140000, N'Đồ uống', N'images/orange_juice.png'),
(N'Nước khoáng', 60000, N'Đồ uống', N'images/mineral_water.png'),
(N'Bánh mì kẹp', 190000, N'Đồ ăn', N'images/burger.png'),
(N'Bánh pizza', 215000, N'Đồ ăn', N'images/pizza_slice.png'),
(N'Mì Ý', 260000, N'Đồ ăn', N'images/pasta.png'),
(N'Cơm cuộn Nhật', 300000, N'Đồ ăn', N'images/sushi.png'),
(N'Kem', 105000, N'Đồ ăn', N'images/ice_cream.png'),
(N'Sinh tố', 165000, N'Đồ uống', N'images/smoothie.png'),
(N'Sô cô la nóng', 130000, N'Đồ uống', N'images/hot_chocolate.png')


-- Bảng tàu
CREATE SEQUENCE TrainSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Train (
    TrainID CHAR(8) PRIMARY KEY DEFAULT ('Train' + RIGHT('000' + CAST(NEXT VALUE FOR TrainSequence AS VARCHAR(3)), 3)),
    TrainNumber VARCHAR(50) NOT NULL,      
    Status NVARCHAR(50) NOT NULL              
);
GO

-- Bảng toa tàu
CREATE TABLE Coach (
    CoachID INT IDENTITY(1,1) PRIMARY KEY,
    CoachNumber INT NOT NULL,      
    CoachType NVARCHAR(50) NOT NULL,         
    Capacity INT NOT NULL,
    TrainID CHAR(8) NOT NULL,              
    FOREIGN KEY (TrainID) REFERENCES Train(TrainID) ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- Bảng ghế
CREATE TABLE Seat (
    SeatID INT IDENTITY(1,1) PRIMARY KEY,
    SeatNumber INT NOT NULL,          
    CoachID int NOT NULL,            
    FOREIGN KEY (CoachID) REFERENCES Coach(CoachID) ON DELETE CASCADE ON UPDATE CASCADE
);
GO


INSERT INTO Train (TrainNumber, Status) VALUES ('SE1', N'Đang hoạt động')
INSERT INTO Train (TrainNumber, Status) VALUES ('SE2', N'Đang hoạt động')

INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (1, N'Ngồi mềm đều hòa', 64, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (2, N'Ngồi mềm đều hòa', 64, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (3, N'Giường nằm khoang 6 điều hòa', 42, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (4, N'Giường nằm khoang 6 điều hòa', 42, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (5, N'Giường nằm khoang 4 điều hòa', 28, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (6, N'Giường nằm khoang 4 điều hòa', 28, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (7, N'Giường nằm khoang 4 điều hòa', 28, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (8, N'Giường nằm khoang 4 điều hòa', 28, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (9, N'Giường nằm khoang 4 điều hòa', 28, 'Train001')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (10, N'Giường nằm khoang 4 điều hòa', 28, 'Train001')

INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (1, N'Ngồi mềm đều hòa', 64, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (2, N'Ngồi mềm đều hòa', 64, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (3, N'Giường nằm khoang 6 điều hòa', 42, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (4, N'Giường nằm khoang 6 điều hòa', 42, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (5, N'Giường nằm khoang 4 điều hòa', 28, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (6, N'Giường nằm khoang 4 điều hòa', 28, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (7, N'Giường nằm khoang 4 điều hòa', 28, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (8, N'Giường nằm khoang 4 điều hòa', 28, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (9, N'Giường nằm khoang 4 điều hòa', 28, 'Train002')
INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (10, N'Giường nằm khoang 4 điều hòa', 28, 'Train002')

select * from coach
go

INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1), (9, 1), (10, 1), (11, 1), (12, 1), (13, 1), (14, 1), (15, 1), (16, 1), (17, 1), (18, 1), (19, 1), (20, 1), (21, 1), (22, 1), (23, 1), (24, 1), (25, 1), (26, 1), (27, 1), (28, 1), (29, 1), (30, 1), (31, 1), (32, 1), (33, 1), (34, 1), (35, 1), (36, 1), (37, 1), (38, 1), (39, 1), (40, 1), (41, 1), (42, 1), (43, 1), (44, 1), (45, 1), (46, 1), (47, 1), (48, 1), (49, 1), (50, 1), (51, 1), (52, 1), (53, 1), (54, 1), (55, 1), (56, 1), (57, 1), (58, 1), (59, 1), (60, 1), (61, 1), (62, 1), (63, 1), (64, 1);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 2), (7, 2), (8, 2), (9, 2), (10, 2), (11, 2), (12, 2), (13, 2), (14, 2), (15, 2), (16, 2), (17, 2), (18, 2), (19, 2), (20, 2), (21, 2), (22, 2), (23, 2), (24, 2), (25, 2), (26, 2), (27, 2), (28, 2), (29, 2), (30, 2), (31, 2), (32, 2), (33, 2), (34, 2), (35, 2), (36, 2), (37, 2), (38, 2), (39, 2), (40, 2), (41, 2), (42, 2), (43, 2), (44, 2), (45, 2), (46, 2), (47, 2), (48, 2), (49, 2), (50, 2), (51, 2), (52, 2), (53, 2), (54, 2), (55, 2), (56, 2), (57, 2), (58, 2), (59, 2), (60, 2), (61, 2), (62, 2), (63, 2), (64, 2);
INSERT INTO Seat (SeatNumber, CoachID)
VALUES (1, 3), (2, 3), (3, 3), (4, 3), (5, 3), (6, 3), (7, 3), (8, 3), (9, 3), (10, 3), (11, 3), (12, 3), (13, 3), (14, 3), (15, 3), (16, 3), (17, 3), (18, 3), (19, 3), (20, 3), (21, 3), (22, 3), (23, 3), (24, 3), (25, 3), (26, 3), (27, 3), (28, 3), (29, 3), (30, 3), (31, 3), (32, 3), (33, 3), (34, 3), (35, 3), (36, 3), (37, 3), (38, 3), (39, 3), (40, 3), (41, 3), (42, 3);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4), (7, 4), (8, 4), (9, 4), (10, 4), (11, 4), (12, 4), (13, 4), (14, 4), (15, 4), (16, 4), (17, 4), (18, 4), (19, 4), (20, 4), (21, 4), (22, 4), (23, 4), (24, 4), (25, 4), (26, 4), (27, 4), (28, 4), (29, 4), (30, 4), (31, 4), (32, 4), (33, 4), (34, 4), (35, 4), (36, 4), (37, 4), (38, 4), (39, 4), (40, 4), (41, 4), (42, 4);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 5), (2, 5), (3, 5), (4, 5), (5, 5), (6, 5), (7, 5), (8, 5), (9, 5), (10, 5), (11, 5), (12, 5), (13, 5), (14, 5), (15, 5), (16, 5), (17, 5), (18, 5), (19, 5), (20, 5), (21, 5), (22, 5), (23, 5), (24, 5), (25, 5), (26, 5), (27, 5), (28, 5);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 6), (2, 6), (3, 6), (4, 6), (5, 6), (6, 6), (7, 6), (8, 6), (9, 6), (10, 6), (11, 6), (12, 6), (13, 6), (14, 6), (15, 6), (16, 6), (17, 6), (18, 6), (19, 6), (20, 6), (21, 6), (22, 6), (23, 6), (24, 6), (25, 6), (26, 6), (27, 6), (28, 6);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 7), (8, 7), (9, 7), (10, 7), (11, 7), (12, 7), (13, 7), (14, 7), (15, 7), (16, 7), (17, 7), (18, 7), (19, 7), (20, 7), (21, 7), (22, 7), (23, 7), (24, 7), (25, 7), (26, 7), (27, 7), (28, 7);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 8), (2, 8), (3, 8), (4, 8), (5, 8), (6, 8), (7, 8), (8, 8), (9, 8), (10, 8), (11, 8), (12, 8), (13, 8), (14, 8), (15, 8), (16, 8), (17, 8), (18, 8), (19, 8), (20, 8), (21, 8), (22, 8), (23, 8), (24, 8), (25, 8), (26, 8), (27, 8), (28, 8);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 9), (2, 9), (3, 9), (4, 9), (5, 9), (6, 9), (7, 9), (8, 9), (9, 9), (10, 9), (11, 9), (12, 9), (13, 9), (14, 9), (15, 9), (16, 9), (17, 9), (18, 9), (19, 9), (20, 9), (21, 9), (22, 9), (23, 9), (24, 9), (25, 9), (26, 9), (27, 9), (28, 9);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 10), (2, 10), (3, 10), (4, 10), (5, 10), (6, 10), (7, 10), (8, 10), (9, 10), (10, 10), (11, 10), (12, 10), (13, 10), (14, 10), (15, 10), (16, 10), (17, 10), (18, 10), (19, 10), (20, 10), (21, 10), (22, 10), (23, 10), (24, 10), (25, 10), (26, 10), (27, 10), (28, 10);

INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 11), (2, 11), (3, 11), (4, 11), (5, 11), (6, 11), (7, 11), (8, 11), (9, 11), (10, 11), (11, 11), (12, 11), (13, 11), (14, 11), (15, 11), (16, 11), (17, 11), (18, 11), (19, 11), (20, 11), (21, 11), (22, 11), (23, 11), (24, 11), (25, 11), (26, 11), (27, 11), (28, 11), (29, 11), (30, 11), (31, 11), (32, 11), (33, 11), (34, 11), (35, 11), (36, 11), (37, 11), (38, 11), (39, 11), (40, 11), (41, 11), (42, 11), (43, 11), (44, 11), (45, 11), (46, 11), (47, 11), (48, 11), (49, 11), (50, 11), (51, 11), (52, 11), (53, 11), (54, 11), (55, 11), (56, 11), (57, 11), (58, 11), (59, 11), (60, 11), (61, 11), (62, 11), (63, 11), (64, 11);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 12), (2, 12), (3, 12), (5, 12), (5, 12), (6, 12), (7, 12), (8, 12), (9, 12), (10, 12), (11, 12), (12, 12), (13, 12), (14, 12), (15, 12), (16, 12), (17, 12), (18, 12), (19, 12), (20, 12), (21, 12), (22, 12), (23, 12), (24, 12), (25, 12), (26, 12), (27, 12), (28, 12), (29, 12), (30, 12), (31, 12), (32, 12), (33, 12), (34, 12), (35, 12), (36, 12), (37, 12), (38, 12), (39, 12), (40, 12), (41, 12), (42, 12), (43, 12), (44, 12), (45, 12), (46, 12), (47, 12), (48, 12), (49, 12), (50, 12), (51, 12), (52, 12), (53, 12), (54, 12), (55, 12), (56, 12), (57, 12), (58, 12), (59, 12), (60, 12), (61, 12), (62, 12), (63, 12), (64, 12);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 13), (2, 13), (3, 13), (4, 13), (5, 13), (6, 13), (7, 13), (8, 13), (9, 13), (10, 13), (11, 13), (12, 13), (13, 13), (14, 13), (15, 13), (16, 13), (17, 13), (18, 13), (19, 13), (20, 13), (21, 13), (22, 13), (23, 13), (24, 13), (25, 13), (26, 13), (27, 13), (28, 13), (29, 13), (30, 13), (31, 13), (32, 13), (33, 13), (34, 13), (35, 13), (36, 13), (37, 13), (38, 13), (39, 13), (40, 13), (41, 13), (42, 13);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 14), (2, 14), (3, 14), (4, 14), (5, 14), (6, 14), (7, 14), (8, 14), (9, 14), (10, 14), (11, 14), (12, 14), (13, 14), (14, 14), (15, 14), (16, 14), (17, 14), (18, 14), (19, 14), (20, 14), (21, 14), (22, 14), (23, 14), (24, 14), (25, 14), (26, 14), (27, 14), (28, 14), (29, 14), (30, 14), (31, 14), (32, 14), (33, 14), (34, 14), (35, 14), (36, 14), (37, 14), (38, 14), (39, 14), (40, 14), (41, 14), (42, 14);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 15), (2, 15), (3, 15), (4, 15), (5, 15), (6, 15), (7, 15), (8, 15), (9, 15), (10, 15), (11, 15), (12, 15), (13, 15), (14, 15), (15, 15), (16, 15), (17, 15), (18, 15), (19, 15), (20, 15), (21, 15), (22, 15), (23, 15), (24, 15), (25, 15), (26, 15), (27, 15), (28, 15);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 16), (2, 16), (3, 16), (4, 16), (5, 16), (6, 16), (7, 16), (8, 16), (9, 16), (10, 16), (11, 16), (12, 16), (13, 16), (14, 16), (15, 16), (16, 16), (17, 16), (18, 16), (19, 16), (20, 16), (21, 16), (22, 16), (23, 16), (24, 16), (25, 16), (26, 16), (27, 16), (28, 16);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 17), (2, 17), (3, 17), (4, 17), (5, 17), (6, 17), (7, 17), (8, 17), (9, 17), (10, 17), (11, 17), (12, 17), (13, 17), (14, 17), (15, 17), (16, 17), (17, 17), (18, 17), (19, 17), (20, 17), (21, 17), (22, 17), (23, 17), (24, 17), (25, 17), (26, 17), (27, 17), (28, 17);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 18), (2, 18), (3, 18), (4, 18), (5, 18), (6, 18), (7, 18), (8, 18), (9, 18), (10, 18), (11, 18), (12, 18), (13, 18), (14, 18), (15, 18), (16, 18), (17, 18), (18, 18), (19, 18), (20, 18), (21, 18), (22, 18), (23, 18), (24, 18), (25, 18), (26, 18), (27, 18), (28, 18);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 19), (2, 19), (3, 19), (4, 19), (5, 19), (6, 19), (7, 19), (8, 19), (9, 19), (10, 19), (11, 19), (12, 19), (13, 19), (14, 19), (15, 19), (16, 19), (17, 19), (18, 19), (19, 19), (20, 19), (21, 19), (22, 19), (23, 19), (24, 19), (25, 19), (26, 19), (27, 19), (28, 19);
INSERT INTO Seat (SeatNumber, CoachID) 
VALUES (1, 20), (2, 20), (3, 20), (4, 20), (5, 20), (6, 20), (7, 20), (8, 20), (9, 20), (10, 20), (11, 20), (12, 20), (13, 20), (14, 20), (15, 20), (16, 20), (17, 20), (18, 20), (19, 20), (20, 20), (21, 20), (22, 20), (23, 20), (24, 20), (25, 20), (26, 20), (27, 20), (28, 20);


SELECT t.TrainID, t.TrainNumber, COUNT(c.CoachID) AS NumberOfCoaches, SUM(c.Capacity) AS TotalCapacity, 
    (SELECT COUNT(DISTINCT CoachType) FROM Coach) AS NumberOfCoachTypes, t.Status
FROM Train t JOIN Coach c ON t.TrainID = c.TrainID 
GROUP BY 
    t.TrainID, 
    t.TrainNumber, 
    t.Status;
go
select * from train
select * from train where TrainNumber LIKE '%%'
select * from coach
/*SELECT COUNT(c.CoachID) AS NumberOfCoaches FROM Train t LEFT JOIN Coach c ON t.TrainID = c.TrainID WHERE T.TrainID = 3 GROUP BY t.TrainID, t.TrainNumber */
SELECT * FROM Coach WHERE TrainID = 'Train001'

-- Bảng đường đi
CREATE SEQUENCE LineSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Line (
    LineID CHAR(12) PRIMARY KEY DEFAULT ('Lin' + RIGHT('000000000' + CAST(NEXT VALUE FOR LineSequence AS VARCHAR(9)), 9)),
    LineName NVARCHAR(255) NOT NULL
);

-- Bảng Chuyến tàu
CREATE SEQUENCE TrainJourneySequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE TrainJourney (
    TrainJourneyID CHAR(12) PRIMARY KEY DEFAULT ('Jou' + RIGHT('000000000' + CAST(NEXT VALUE FOR TrainJourneySequence AS VARCHAR(9)), 9)),
    TrainJourneyName NVARCHAR(255) NOT NULL,
    TrainID char(8) NOT NULL,
	LineID char(12) not null,
    BasePrice Money NOT NULL,
	CONSTRAINT FK_Line FOREIGN KEY (LineID) REFERENCES Line(LineID),
    CONSTRAINT FK_Train FOREIGN KEY (TrainID) REFERENCES Train(TrainID)
);

-- Bảng Trạm
CREATE SEQUENCE StationSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Station (
    StationID CHAR(12) PRIMARY KEY DEFAULT ('Sta' + RIGHT('000000000' + CAST(NEXT VALUE FOR StationSequence AS VARCHAR(9)), 9)),
    StationName NVARCHAR(255) NOT NULL
);

-- Bảng trung gian Đường - Điểm dừng
CREATE TABLE LineStop (
    LineID char(12) NOT NULL, 
    StationID char(12) NOT NULL,
    StopOrder INT NOT NULL, 
    Distance INT NOT NULL,

    PRIMARY KEY (LineID, StationID),

    CONSTRAINT FK_LineStop FOREIGN KEY (LineID) REFERENCES Line(LineID) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Station FOREIGN KEY (StationID) REFERENCES Station(StationID) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Điểm dừng
CREATE SEQUENCE StopSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Stop (
    StopID CHAR(12) PRIMARY KEY DEFAULT ('Sto' + RIGHT('000000000' + CAST(NEXT VALUE FOR StopSequence AS VARCHAR(9)), 9)),
    trainJourneyID CHAR(12) NOT NULL,           -- Foreign key to TrainJourney table
    stationID CHAR(12) NOT NULL,                -- Foreign key to Station table
    stopOrder INT NOT NULL,
    distance INT NOT NULL,
    departureDate DATE NOT NULL,
    arrivalTime TIME NOT NULL,
    departureTime TIME NOT NULL,
    CONSTRAINT FK_TrainJourney FOREIGN KEY (trainJourneyID) REFERENCES TrainJourney(trainJourneyID),
    CONSTRAINT FK_StopStation FOREIGN KEY (stationID) REFERENCES Station(stationID)
);

-- Bảng Hành khách
CREATE SEQUENCE PassengerSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Passenger (
    PassengerID CHAR(12) PRIMARY KEY DEFAULT ('Pas' + RIGHT('000000000' + CAST(NEXT VALUE FOR PassengerSequence AS VARCHAR(9)), 9)),
    FullName NVARCHAR(255) NOT NULL, 
	DateOfBirth Date,
    Identifier NVARCHAR(50),	
	IdentifierType nvarchar(20), 
    PassengerType NVARCHAR(50) NOT NULL,
);
INSERT INTO Passenger (FullName, DateOfBirth, Identifier, IdentifierType, PassengerType)
VALUES 
    (N'Đoàn Nguyễn Hồng Phúc', '1985-01-15', '123456789', 'CMND', N'Người lớn'),
    (N'Trần Thị B', '1990-05-20', '987654321', 'CMND', N'Người lớn'),
    (N'Lê Văn C', '2013-12-10', '072205663217', 'CCCD', N'Sinh Viên'),
    (N'Phạm Thị D', '2004-07-25', '998877665', 'CCCD', N'Người lớn'),
    (N'Nguyễn Văn E', '2012-04-30',  null, null, N'Trẻ em');
go

-- Bảng Khách hàng
CREATE SEQUENCE CustomerSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Customer (
    CustomerID CHAR(12) PRIMARY KEY DEFAULT ('Cus' + RIGHT('000000000' + CAST(NEXT VALUE FOR CustomerSequence AS VARCHAR(9)), 9)),
    fullName NVARCHAR(255) NOT NULL,
    phoneNumber NVARCHAR(15) NOT NULL, 
    email NVARCHAR(255) NOT NULL UNIQUE,
    identificationNumber NVARCHAR(50) NOT NULL
);
INSERT INTO Customer (fullName, phoneNumber, email, identificationNumber)
VALUES
(N'Nguyễn Văn A', '0909123456', N'nguyenvana@gmail.com', '123456789'),
(N'Lê Thị B', '0910234567', N'lethib@gmail.com', '987654321'),
(N'Trần Văn C', '0921345678', N'tranvanc@gmail.com', '456789123'),
(N'Phạm Đức D', '0921345448', N'phamvand@gmail.com', '456789321');
go

-- Bảng Đơn đặt
CREATE SEQUENCE OrderSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE [Order] (
    OrderID CHAR(12) PRIMARY KEY DEFAULT ('Odr' + RIGHT('000000000' + CAST(NEXT VALUE FOR OrderSequence AS VARCHAR(9)), 9)),
    OrderDate DATETIME NOT NULL,
    Note NVARCHAR(255),
    TimeRemaining TIME NOT NULL,
	OrderStatus nvarchar(30) not null,
	TaxID char(6) not null,
    CustomerID CHAR(12) NOT NULL,
    TrainJourneyID CHAR(12) NOT NULL,
    EmployeeID CHAR(12) NOT NULL,
	CONSTRAINT chk_OrderStatus CHECK (OrderStatus IN (N'Đã Thanh Toán', N'Chưa Thanh Toán', N'Đã Hủy', N'Đã Đổi')),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (TrainJourneyID) REFERENCES TrainJourney(TrainJourneyID),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
    FOREIGN KEY (TaxID) REFERENCES Tax(TaxID)
);

-- Bảng chi tiết dịch vụ
create table ServiceDetail (
	ServiceID char(12) not null,
	OrderID char(12) not null,
	PromotionID char(6),
	quantity int not null CHECK (quantity > 0),
	primary key (ServiceID, OrderID),
	FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID) ON DELETE CASCADE,
    FOREIGN KEY (OrderID) REFERENCES [Order](OrderID) ON DELETE CASCADE,
	FOREIGN KEY (PromotionID) REFERENCES Promotion(PromotionID)
); 

-- Bảng vé
CREATE SEQUENCE TicketSequence
    START WITH 1
    INCREMENT BY 1;
go
CREATE TABLE Ticket (
    TicketID CHAR(12) PRIMARY KEY DEFAULT ('Tic' + RIGHT('000000000' + CAST(NEXT VALUE FOR TicketSequence AS VARCHAR(9)), 9)),
    TrainJourneyID CHAR(12) NOT NULL,  -- Foreign key reference to TrainJourney
    SeatID int NOT NULL,  -- Foreign key reference to Seat
    PassengerID CHAR(12) NOT NULL,  -- Foreign key reference to Passenger
    OrderID CHAR(12) NOT NULL,  -- Foreign key reference to Order
	PromotionID char(6),
    FOREIGN KEY (TrainJourneyID) REFERENCES TrainJourney(trainJourneyID),
    FOREIGN KEY (SeatID) REFERENCES Seat(SeatID),
    FOREIGN KEY (PassengerID) REFERENCES Passenger(PassengerID),
    FOREIGN KEY (OrderID) REFERENCES [Order](OrderID),
	FOREIGN KEY (PromotionID) REFERENCES Promotion(PromotionID)
);

-- Bảng chi tiết vé
CREATE TABLE TicketDetail (
    StopID CHAR(12) NOT NULL,
    TicketID CHAR(12) NOT NULL,
    PRIMARY KEY (StopID, TicketID), 
    FOREIGN KEY (StopID) REFERENCES Stop(StopID),
    FOREIGN KEY (TicketID) REFERENCES Ticket(TicketID)
);

INSERT INTO Line (lineName) VALUES (N'Đà Lạt - Trại Mát')
INSERT INTO Line (lineName) VALUES (N'Sài Gòn - Hà Nội')
INSERT INTO Line (lineName) VALUES (N'Hà Nội - Ninh Bình')

INSERT INTO TrainJourney (trainJourneyName, trainID, lineID, basePrice) VALUES (N'Đà lạt - Trại Mát 9:55am', 'Train001', 'Lin000000001', 2000);
INSERT INTO TrainJourney (trainJourneyName, trainID, lineID, basePrice) VALUES (N'Sài Gòn - Hà Nội 9:30am', 'Train002', 'Lin000000002', 2000);
INSERT INTO TrainJourney (trainJourneyName, trainID, lineID, basePrice) VALUES (N'Hà Nội - Ninh Bình 10:30am', 'Train001', 'Lin000000003', 1500);

INSERT INTO Station (stationName) VALUES (N'Đà Lạt')
INSERT INTO Station (stationName) VALUES (N'Trại Mát')

INSERT INTO Station (stationName) VALUES (N'Sài Gòn')
INSERT INTO Station (stationName) VALUES (N'Dĩ An');
INSERT INTO Station (stationName) VALUES (N'Biên Hòa');
INSERT INTO Station (stationName) VALUES (N'Long Khánh');
INSERT INTO Station (stationName) VALUES (N'Suối Kiết');
INSERT INTO Station (stationName) VALUES (N'Bình Thuận');
INSERT INTO Station (stationName) VALUES (N'Tháp Chàm');
INSERT INTO Station (stationName) VALUES (N'Nha Trang');
INSERT INTO Station (stationName) VALUES (N'Ninh Hòa');
INSERT INTO Station (stationName) VALUES (N'Giã');
INSERT INTO Station (stationName) VALUES (N'Tuy Hòa');
INSERT INTO Station (stationName) VALUES (N'La Hai');
INSERT INTO Station (stationName) VALUES (N'Diêu Trì');
INSERT INTO Station (stationName) VALUES (N'Bồng Sơn');
INSERT INTO Station (stationName) VALUES (N'Quảng Ngãi');
INSERT INTO Station (stationName) VALUES (N'Tam Kỳ');
INSERT INTO Station (stationName) VALUES (N'Đà Nẵng');
INSERT INTO Station (stationName) VALUES (N'Huế');
INSERT INTO Station (stationName) VALUES (N'Đông Hà');
INSERT INTO Station (stationName) VALUES (N'Mỹ Đức');
INSERT INTO Station (stationName) VALUES (N'Đồng Hới');
INSERT INTO Station (stationName) VALUES (N'Minh Lệ');
INSERT INTO Station (stationName) VALUES (N'Đồng Lê');
INSERT INTO Station (stationName) VALUES (N'Hương Phố');
INSERT INTO Station (stationName) VALUES (N'Yên Trung');
INSERT INTO Station (stationName) VALUES (N'Vinh');
INSERT INTO Station (stationName) VALUES (N'Chợ Sy');
INSERT INTO Station (stationName) VALUES (N'Minh Khôi');
INSERT INTO Station (stationName) VALUES (N'Thanh Hóa');
INSERT INTO Station (stationName) VALUES (N'Bỉm Sơn');
INSERT INTO Station (stationName) VALUES (N'Ninh Bình');
INSERT INTO Station (stationName) VALUES (N'Nam Định');
INSERT INTO Station (stationName) VALUES (N'Phủ Lý');
INSERT INTO Station (stationName) VALUES (N'Hà Nội')



INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000001', 'Sta000000001', 1, 0)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000001', 'Sta000000002', 2, 7)

INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000003', 1, 0); -- Sài Gòn
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000004', 2, 19); -- Dĩ An
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000005', 3, 29); -- Biên Hòa
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000006', 4, 77); -- Long Khánh
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000007', 5, 123); -- Suối Kiết
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000008', 6, 175); -- Bình Thuận
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000009', 7, 318); -- Tháp Chàm
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000010', 8, 411); -- Nha Trang
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000011', 9, 418); -- Ninh Hòa
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000012', 10, 472); -- Gia
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000013', 11, 528); -- Tuy Hòa
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000014', 12, 572); -- La Hai
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000015', 13, 630); -- Diêu Trì
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000016', 14, 709); -- Bồng Sơn
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000017', 15, 798); -- Quảng Ngãi
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000018', 16, 861); -- Tam Kỳ
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000019', 17, 935); -- Đà Nẵng
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000020', 18, 1038); -- Huế
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000021', 19, 1104); -- Đông Hà
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000022', 20, 1175); -- Mỹ Đức
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000023', 21, 1204); -- Đồng Hới
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000024', 22, 1244); -- Minh Lệ
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000025', 23, 1290); -- Đồng Lê
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000026', 24, 1339); -- Hương Phố
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000027', 25, 1386); -- Yên Trung
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000028', 26, 1407); -- Vinh
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000029', 27, 1447); -- Chợ Sy
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000030', 28, 1529); -- Minh Khôi
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000031', 29, 1551); -- Thanh Hóa
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000032', 30, 1585); -- Bỉm Sơn
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000033', 31, 1611); -- Ninh Bình
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000034', 32, 1639); -- Nam Định
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000035', 33, 1670); -- Phủ Lý
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000002', 'Sta000000036', 34, 1726); -- Hà Nội

INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000036', 1, 0)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000035', 2, 56)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000034', 3, 87)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000033', 4, 115)

INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000001', 'Sta000000001', 1, 0, '2024-10-16', '09:55:00', '09:55:00')
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000001', 'Sta000000002', 2, 7, '2024-10-16', '10:25:00', '10:35:00')

INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000003', 1, 0, '2024-10-22', '06:00:00', '06:00:00')
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000004', 2, 19, '2024-10-22', '06:29:00', '06:32:00')
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000005', 3, 29, '2024-10-22', '06:45:00', '06:48:00')
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000006', 4, 77, '2024-10-22', '07:48:00', '07:51:00')
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000007', 5, 123, '2024-10-22', '08:39:00', '08:43:00')
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000008', 6, 175, '2024-10-22', '09:41:00', '09:46:00')
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000009', 7, 318, '2024-10-22', '12:01:00', '12:04:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000010', 8, 411, '2024-10-22', '13:39:00', '13:59:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000011', 9, 445, '2024-10-22', '14:41:00', '14:44:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000012', 10, 472, '2024-10-22', '15:12:00', '15:15:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000013', 11, 528, '2024-10-22', '16:17:00', '16:21:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000014', 12, 572, '2024-10-22', '17:16:00', '17:20:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000015', 13, 630, '2024-10-22', '18:29:00', '18:41:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000016', 14, 709, '2024-10-22', '20:03:00', '20:06:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000017', 15, 798, '2024-10-22', '21:48:00', '21:53:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000018', 16, 861, '2024-10-22', '23:02:00', '23:05:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000019', 17, 935, '2024-10-23', '00:36:00', '00:56:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000020', 18, 1038, '2024-10-23', '03:28:00', '03:34:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000021', 19, 1104, '2024-10-23', '04:46:00', '04:49:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000022', 20, 1175, '2024-10-23', '06:06:00', '06:15:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000023', 21, 1204, '2024-10-23', '06:50:00', '07:02:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000024', 22, 1244, '2024-10-23', '07:48:00', '07:51:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000025', 23, 1290, '2024-10-23', '08:46:00', '08:49:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000026', 24, 1339, '2024-10-23', '09:54:00', '09:57:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000027', 25, 1386, '2024-10-23', '10:53:00', '10:56:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000028', 26, 1407, '2024-10-23', '11:22:00', '11:29:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000029', 27, 1447, '2024-10-23', '12:32:00', '12:35:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000030', 28, 1529, '2024-10-23', '14:12:00', '14:15:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000031', 29, 1551, '2024-10-23', '14:38:00', '14:41:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000032', 30, 1585, '2024-10-23', '15:16:00', '15:19:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000033', 31, 1611, '2024-10-23', '16:09:00', '16:12:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000034', 32, 1639, '2024-10-23', '16:48:00', '17:08:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000035', 33, 1670, '2024-10-23', '17:49:00', '17:52:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000002', 'Sta000000036', 34, 1726, '2024-10-23', '19:12:00', '19:12:00');

INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000003', 'Sta000000036', 1, 0, '2024-10-25', '06:10:00', '06:10:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000003', 'Sta000000035', 2, 56, '2024-10-25', '07:12:00', '07:15:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000003', 'Sta000000034', 3, 87, '2024-10-25', '07:48:00', '07:51:00');
INSERT INTO Stop (trainJourneyID, stationID, stopOrder, distance, departureDate, arrivalTime, departureTime) 
VALUES ('Jou000000003', 'Sta000000033', 4, 125, '2024-10-25', '08:23:00', '08:26:00');


INSERT INTO Customer (fullName, phoneNumber, email, identificationNumber) VALUES (N'Phạm Đức Tài', '0846107843', 'phamductai10272003@gmail.com', '080203001008')

INSERT INTO [Order] (OrderDate, Note, TimeRemaining, OrderStatus, TaxID, CustomerID, TrainJourneyID, EmployeeID)
VALUES
(GETDATE(), 'First order', '01:30:00', N'Đã Thanh Toán', 'Tax001', 'Cus000000001', 'Jou000000001', 'Emp000000001'),
(GETDATE(), 'Second order', '02:00:00', N'Chưa Thanh Toán', 'Tax001', 'Cus000000002', 'Jou000000001', 'Emp000000001'),
(GETDATE(), 'Third order', '03:00:00', N'Đã Hủy', 'Tax001', 'Cus000000003', 'Jou000000001', 'Emp000000001'),
(GETDATE(), 'Fourth order', '01:45:00', N'Đã Đổi', 'Tax001', 'Cus000000004', 'Jou000000001', 'Emp000000001'),
('2024-10-17 18:05:05', 'Fifth order', '05:45:00', N'Đã Thanh Toán', 'Tax001', 'Cus000000001', 'Jou000000002', 'Emp000000001');

INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000001', 1, 'Pas000000001', 'Odr000000001')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000001', 2, 'Pas000000002', 'Odr000000001')

INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000001', 4, 'Pas000000003', 'Odr000000002')

INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000002', 44, 'Pas000000001', 'Odr000000005')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000002', 45, 'Pas000000002', 'Odr000000005')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000002', 46, 'Pas000000003', 'Odr000000005')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000002', 47, 'Pas000000004', 'Odr000000005')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000002', 593, 'Pas000000005', 'Odr000000005')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000003', 593, 'Pas000000001', 'Odr000000005')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000003', 594, 'Pas000000002', 'Odr000000005')
INSERT INTO Ticket (trainJourneyID, seatID, passengerID, orderID) VALUES ('Jou000000003', 594, 'Pas000000003', 'Odr000000005')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000001', 'Tic000000001')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000002', 'Tic000000001')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000001', 'Tic000000002')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000001', 'Tic000000003')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000002', 'Tic000000003')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000004', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000005', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000006', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000007', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000008', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000009', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000010', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000011', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000012', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000013', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000014', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000015', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000016', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000017', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000018', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000019', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000020', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000021', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000022', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000023', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000024', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000025', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000026', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000027', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000028', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000029', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000030', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000031', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000032', 'Tic000000004')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000033', 'Tic000000004')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000004', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000005', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000006', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000007', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000008', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000009', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000010', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000011', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000012', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000013', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000014', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000015', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000016', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000017', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000018', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000019', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000020', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000021', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000022', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000023', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000024', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000025', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000026', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000027', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000028', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000029', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000030', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000031', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000032', 'Tic000000005')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000033', 'Tic000000005')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000004', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000005', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000006', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000007', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000008', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000009', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000010', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000011', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000012', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000013', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000014', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000015', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000016', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000017', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000018', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000019', 'Tic000000006')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000020', 'Tic000000006')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000004', 'Tic000000007')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000005', 'Tic000000007')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000006', 'Tic000000007')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000007', 'Tic000000007')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000008', 'Tic000000007')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000009', 'Tic000000007')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000010', 'Tic000000007')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000004', 'Tic000000008')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000005', 'Tic000000008')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000006', 'Tic000000008')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000007', 'Tic000000008')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000008', 'Tic000000008')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000009', 'Tic000000008')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000010', 'Tic000000008')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000037', 'Tic000000009')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000038', 'Tic000000009')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000039', 'Tic000000009')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000040', 'Tic000000009')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000037', 'Tic000000010')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000038', 'Tic000000010')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000039', 'Tic000000010')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000040', 'Tic000000010')

INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000038', 'Tic000000011')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000039', 'Tic000000011')
INSERT INTO TicketDetail (stopID, ticketID) VALUES ('Sto000000040', 'Tic000000011')

INSERT INTO Line (lineName) VALUES (N'Sài Gòn - Nha Trang')

select * from Station

select * from line

INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000003', 1, 0)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000004', 2, 19)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000005', 3, 29)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000009', 4, 318)
INSERT INTO LineStop (lineID, stationID, stopOrder, distance) VALUES ('Lin000000003', 'Sta000000010', 5, 411)

select stopOrder, s.stationID, s.stationName, distance 
from line l join LineStop ls on l.lineID = ls.lineID join station s on ls.stationID = s.stationID 
where l.lineID = 'Lin000000003'
/*
{ "Mã chuyến tàu", "Số hiệu tàu", "Hành trình", "Thời gian", "Cự ly", "Tổng hành khách")

WRITE A QUERY TO TAKE OUT THESE COLUMNS TrainJourneyID, trainID, train journey departureStation - arrivalStation as Journey, trainJourney departuretime - arrivaltime as time, train journey distance, ticketbooked/numberOfSeats as Book

SELECT tj.trainJourneyID, tj.trainID, CONCAT(s1.stationName, ' - ', s2.stationName) AS Journey, CONCAT(st1.departureTime, ' - ', st2.arrivalTime) AS Time, SUM(st1.distance) AS Distance, COUNT(t.ticketID) / SUM(c.Capacity) AS Book
FROM TrainJourney tj JOIN Stop st1 ON tj.trainJourneyID = st1.trainJourneyID JOIN  Stop st2 ON tj.trainJourneyID = st2.trainJourneyID AND st2.stopOrder = (
        SELECT MAX(stopOrder) FROM Stop WHERE trainJourneyID = tj.trainJourneyID) JOIN Station s1 ON st1.stationID = s1.stationID JOIN 
    Station s2 ON st2.stationID = s2.stationID JOIN 
    Ticket t ON tj.trainJourneyID = t.trainJourneyID JOIN 
    Seat se ON t.seatID = se.SeatID JOIN Coach c ON se.CoachID = c.CoachID
GROUP BY tj.trainJourneyID, tj.trainID, s1.stationName, s2.stationName, st1.departureTime, st2.arrivalTime;
*/

-- trainJourneyID, trainJourneyName, TrainNumber
SELECT trainJourneyID, trainJourneyName, TrainNumber
FROM TrainJourney tj
JOIN Train t ON tj.trainID = t.TrainID

-- depatureStation and departureTime
SELECT st.stationName, sp.departureTime
FROM TrainJourney tj JOIN Stop sp ON tj.trainJourneyID = sp.trainJourneyID JOIN Station st ON sp.stationID = st.stationID
WHERE tj.trainJourneyID = 'Jou000000001' AND stopOrder = 1

-- arrivalStaion and arrivalTime
SELECT st.stationName AS arrivalStation,sp.arrivalTime AS arrivalTime,distance
FROM TrainJourney tj JOIN  Stop sp ON tj.trainJourneyID = sp.trainJourneyID JOIN Station st ON sp.stationID = st.stationID
WHERE tj.trainJourneyID = 'Jou000000001' AND sp.stopOrder = (
        SELECT MAX(stopOrder) 
        FROM Stop 
        WHERE trainJourneyID = tj.trainJourneyID);

-- number of seats
select count(SeatNumber) as NumberOfSeats
from train t join coach c on t.TrainID = c.TrainID join seat s on c.CoachID = s.CoachID
where t.TrainID = 'Train001'

select count(ticketID) as BookedTicket
from Ticket
where trainJourneyID = 'Jou000000001'
--------------------------------------
--------------------------------------
--------------------------------------
--------------------------------------

go
CREATE or alter FUNCTION dbo.fn_GetAllTrainJourneyDetails()
RETURNS TABLE
AS
RETURN
(
    SELECT 
        tj.trainJourneyID,
        tj.trainJourneyName,
        t.TrainNumber,
        
        -- Departure station and time
        (SELECT 
            st1.stationName
         FROM 
            Stop sp1
         JOIN 
            Station st1 ON sp1.stationID = st1.stationID
         WHERE 
            sp1.trainJourneyID = tj.trainJourneyID 
            AND sp1.stopOrder = 1) AS departureStation,
        
        (SELECT 
            sp1.departureTime
         FROM 
            Stop sp1
         WHERE 
            sp1.trainJourneyID = tj.trainJourneyID 
            AND sp1.stopOrder = 1) AS departureTime,

		(SELECT 
            sp1.departureDate
         FROM 
            Stop sp1
         WHERE 
            sp1.trainJourneyID = tj.trainJourneyID 
            AND sp1.stopOrder = 1) AS departureDate,
        
        -- Arrival station and time
        (SELECT 
            st2.stationName
         FROM 
            Stop sp2
         JOIN 
            Station st2 ON sp2.stationID = st2.stationID
         WHERE 
            sp2.trainJourneyID = tj.trainJourneyID 
            AND sp2.stopOrder = (
                SELECT MAX(stopOrder) 
                FROM Stop 
                WHERE trainJourneyID = tj.trainJourneyID
            )) AS arrivalStation,
        
        (SELECT 
            sp2.arrivalTime
         FROM 
            Stop sp2
         WHERE 
            sp2.trainJourneyID = tj.trainJourneyID 
            AND sp2.stopOrder = (
                SELECT MAX(stopOrder) 
                FROM Stop 
                WHERE trainJourneyID = tj.trainJourneyID
            )) AS arrivalTime,
            
        -- Total distance of journey
        (SELECT 
            max(distance)
         FROM 
            Stop
         WHERE 
            trainJourneyID = tj.trainJourneyID) AS totalDistance,

        -- Total number of seats in the train
        (SELECT 
            COUNT(s.SeatNumber)
         FROM 
            Train t1
         JOIN 
            Coach c ON t1.TrainID = c.TrainID
         JOIN 
            Seat s ON c.CoachID = s.CoachID
         WHERE 
            t1.TrainID = t.TrainID) AS totalSeats,

        -- Booked tickets for the journey
        (SELECT 
            COUNT(ticketID)
         FROM 
            Ticket
         WHERE 
            trainJourneyID = tj.trainJourneyID) AS bookedTickets

    FROM 
        TrainJourney tj
    JOIN 
        Train t ON tj.trainID = t.TrainID
);
go

SELECT *
FROM dbo.fn_GetAllTrainJourneyDetails();
go   



CREATE   FUNCTION [dbo].[getEmployeeByAccount](@user VARCHAR(50))
RETURNS TABLE
AS
RETURN
SELECT e.*
FROM Employee e JOIN Account a 
ON e.EmployeeID = a.EmployeeID
WHERE a.Username = @user;
GO


CREATE or alter FUNCTION dbo.GetAllTrainDetails()
RETURNS TABLE
AS
RETURN
(
    SELECT 
        t.TrainID,
        t.TrainNumber,
        COUNT(c.CoachID) AS NumberOfCoaches,
        SUM(c.Capacity) AS Capacity,
        COUNT(DISTINCT c.CoachType) AS NumberOfCoachTypes,
        STUFF((
            SELECT DISTINCT ', ' + c2.CoachType
            FROM Coach c2
            WHERE c2.TrainID = t.TrainID
            FOR XML PATH('')), 1, 2, '') AS CoachTypes,
        t.Status
    FROM 
        Train t
    LEFT JOIN 
        Coach c ON t.TrainID = c.TrainID
    GROUP BY 
        t.TrainID, t.TrainNumber, t.Status
);
go

CREATE OR ALTER TRIGGER trg_UpdateAccountOnEmployeeDelete
ON Employee
INSTEAD OF DELETE
AS
BEGIN
    DELETE FROM Account
    WHERE EmployeeID IN (SELECT EmployeeID FROM DELETED);

	DELETE FROM Employee
    WHERE EmployeeID IN (SELECT EmployeeID FROM DELETED);
END;
go 

select * from Ticket t join fn_GetAllTrainJourneyDetails() j on j.trainJourneyID = t.TrainJourneyID

go

CREATE OR ALTER PROCEDURE GetAllTicketsByOrderID @OrderID NVARCHAR(50) AS
BEGIN
    SET NOCOUNT ON;
    WITH FirstDeparture AS (
	SELECT td.TicketID, MIN(TRY_CAST(CONCAT(CONVERT(VARCHAR(10), s.departureDate, 120), ' ', CONVERT(VARCHAR(8), s.departureTime, 108)) AS DATETIME)) AS FirstDepartureTime
	FROM TicketDetail td JOIN Stop s ON s.StopID = td.StopID 
	GROUP BY td.TicketID
	)
	SELECT DISTINCT t.TicketID, PassengerID, t.TrainJourneyID, fd.FirstDepartureTime AS DepartureDateTime, c.CoachID, se.SeatID, o.OrderStatus, distanceValues.MaxDistance - distanceValues.MinDistance AS distance
	FROM Ticket t JOIN TicketDetail td ON t.TicketID = td.TicketID JOIN FirstDeparture fd ON fd.TicketID = t.TicketID JOIN [Order] o ON t.OrderID = o.OrderID JOIN Seat se ON se.SeatID = t.SeatID JOIN Coach c ON c.CoachID = se.CoachID JOIN (
	SELECT td.TicketID, MAX(s.distance) AS MaxDistance, MIN(s.distance) AS MinDistance
	FROM TicketDetail td JOIN Stop s ON s.StopID = td.StopID GROUP BY td.TicketID) AS distanceValues ON t.TicketID = distanceValues.TicketID
	WHERE o.OrderID = @OrderID
	ORDER BY t.TicketID;
END;
go

EXEC GetAllTicketsByOrderID @OrderID = 'Odr000000005';
EXEC GetAllTicketsByOrderID @OrderID = 'Odr000000002';

select * from fn_GetAllTrainJourneyDetails()

go

CREATE or alter FUNCTION dbo.GetDistanceBetweenStops(
    @trainJourneyID char(12),
    @stationID1 char(12),
    @stationID2 char(12)
) 
RETURNS DECIMAL(10, 2) 
AS
BEGIN
    DECLARE @distance DECIMAL(10, 2);

    SELECT 
        @distance = ABS(s1.distance - s2.distance)
    FROM 
        Stop s1
    JOIN 
        Stop s2 
        ON s1.trainJourneyID = s2.trainJourneyID
    WHERE 
        s1.trainJourneyID = @trainJourneyID
        AND s1.stationID = @stationID1
        AND s2.stationID = @stationID2;

    RETURN @distance;
END;
go

--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
--=======================================================================================================--
CREATE or alter FUNCTION dbo.GetTrainJourneysByStationNames (@departureStationName NVARCHAR(255), @arrivalStationName NVARCHAR(255), @departureDate DATE)
RETURNS TABLE AS RETURN (
    SELECT 
		tj.trainJourneyID,
        tj.trainJourneyName,
        t.TrainID,
        t.TrainNumber,
        ds.stationID AS departureStationID,
        dep_station.stationName AS departureStationName,  -- Added departure station name
        ds.departureDate,
        ds.departureTime,
        arrival_stop.stationID AS arrivalStationID,
        arr_station.stationName AS arrivalStationName,  -- Added arrival station name
        arrival_stop.departureDate AS arrivalDate,
        arrival_stop.arrivalTime,
        -- Calculate journey duration in minutes
        DATEDIFF(MINUTE, 
                 CAST(ds.departureDate AS DATETIME) + CAST(ds.departureTime AS DATETIME),  -- Departure date and time
                 CAST(arrival_stop.departureDate AS DATETIME) + CAST(arrival_stop.arrivalTime AS DATETIME)  -- Arrival date and time
        ) AS journeyDuration,  -- Duration in minutes

        -- Calculate available seats by subtracting booked seats from total seats
        (SELECT COUNT(*) 
         FROM Seat s
         JOIN Coach c ON s.CoachID = c.CoachID
         WHERE c.TrainID = tj.trainID
        ) - 
        (SELECT COUNT(*) 
         FROM Ticket t
         JOIN TicketDetail td ON t.ticketID = td.ticketID
         WHERE t.trainJourneyID = tj.trainJourneyID
           AND td.stopID BETWEEN ds.stopID AND arrival_stop.stopID  -- Ensure tickets are for this leg of the journey
        ) AS numberOfAvailableSeatsLeft  -- Available seats
    FROM 
        TrainJourney tj
    JOIN 
        Train t ON t.TrainID = tj.trainID
    JOIN 
        Stop ds ON tj.trainJourneyID = ds.trainJourneyID  -- Departure stop
    JOIN 
        Stop arrival_stop ON tj.trainJourneyID = arrival_stop.trainJourneyID  -- Arrival stop
    JOIN
        Station dep_station ON ds.stationID = dep_station.stationID  -- Departure station
    JOIN
        Station arr_station ON arrival_stop.stationID = arr_station.stationID  -- Arrival station
    WHERE 
        dep_station.stationName = @departureStationName
        AND arr_station.stationName = @arrivalStationName
        AND ds.departureDate = @departureDate
        AND ds.stopOrder < arrival_stop.stopOrder  -- Ensure departure stop is before arrival stop
    -- Using TOP 1000 to allow ORDER BY
    ORDER BY 
        ds.departureTime
    OFFSET 0 ROWS
);
go

-----------------------------------------------
CREATE or alter FUNCTION GetStopsForJourney (@trainJourneyID char(12), @departureStationID char(12), @arrivalStationID char(12))
RETURNS TABLE AS RETURN (
    SELECT 
        s1.stopID, 
        s1.trainJourneyID, 
        s1.stationID, 
        st.stationName, 
        s1.stopOrder, 
        s1.distance, 
        s1.departureDate, 
        s1.arrivalTime, 
        s1.departureTime
    FROM 
        Stop s1
    JOIN 
        Station st ON s1.stationID = st.stationID
    JOIN 
        Stop s2 ON s1.trainJourneyID = s2.trainJourneyID
    JOIN 
        Stop s3 ON s1.trainJourneyID = s3.trainJourneyID
    WHERE 
        s1.trainJourneyID = @trainJourneyID
        AND s2.stationID = @departureStationID 
        AND s3.stationID = @arrivalStationID 
        AND s1.stopOrder BETWEEN s2.stopOrder AND s3.stopOrder
);
go

----------------------------------------
CREATE or alter FUNCTION dbo.fn_GetUnavailableSeats (@trainJourneyID char(12), @departureStationID char(12), @arrivalStationID char(12)) 
RETURNS TABLE AS RETURN
WITH BookedSeats AS (
    SELECT t.seatID, MIN(stop1.stopOrder) AS bookedStartOrder, MAX(stop2.stopOrder) AS bookedEndOrder
    FROM Ticket t
    JOIN TicketDetail td1 ON t.ticketID = td1.ticketID
    JOIN Stop stop1 ON td1.stopID = stop1.stopID  
    JOIN TicketDetail td2 ON t.ticketID = td2.ticketID
    JOIN Stop stop2 ON td2.stopID = stop2.stopID  
    WHERE t.trainJourneyID = @trainJourneyID
    GROUP BY t.seatID
)
SELECT s.SeatID, s.SeatNumber, c.CoachNumber, c.CoachType
FROM Seat s
JOIN Coach c ON s.CoachID = c.CoachID
JOIN BookedSeats bs ON s.SeatID = bs.seatID
JOIN Stop depStop ON depStop.stationID = @departureStationID 
                  AND depStop.trainJourneyID = @trainJourneyID
JOIN Stop arrStop ON arrStop.stationID = @arrivalStationID 
                  AND arrStop.trainJourneyID = @trainJourneyID
WHERE depStop.stopOrder < arrStop.stopOrder  -- Ensure correct journey direction
    AND (
        depStop.stopOrder BETWEEN bs.bookedStartOrder AND bs.bookedEndOrder
        OR arrStop.stopOrder BETWEEN bs.bookedStartOrder AND bs.bookedEndOrder
        OR bs.bookedStartOrder BETWEEN depStop.stopOrder AND arrStop.stopOrder
    )
go

------------------------
--=============
---------------------
CREATE OR ALTER FUNCTION GetLineDetails()
RETURNS TABLE
AS
RETURN
WITH LineStops AS (
    SELECT 
        ls.LineID,
        s.StationName AS StationName,
        ls.StopOrder,
        ls.Distance
    FROM LineStop ls
    JOIN Station s ON ls.StationID = s.StationID
),
DepartureStations AS (
    SELECT
        LineID,
        StationName AS DepartureStation,
        Distance AS DepartureDistance
    FROM LineStops
    WHERE StopOrder = (SELECT MIN(StopOrder) FROM LineStop WHERE LineStop.LineID = LineStops.LineID)
),
ArrivalStations AS (
    SELECT
        LineID,
        StationName AS ArrivalStation,
        Distance AS ArrivalDistance
    FROM LineStops
    WHERE StopOrder = (SELECT MAX(StopOrder) FROM LineStop WHERE LineStop.LineID = LineStops.LineID)
),
TotalDistances AS (
    SELECT
        LineID,
        MAX(Distance) AS TotalDistance
    FROM LineStop
    GROUP BY LineID
)
SELECT 
    l.LineID,
    l.LineName,
    d.DepartureStation,
    a.ArrivalStation,
    t.TotalDistance
FROM Line l
LEFT JOIN DepartureStations d ON l.LineID = d.LineID
LEFT JOIN ArrivalStations a ON l.LineID = a.LineID
LEFT JOIN TotalDistances t ON l.LineID = t.LineID
