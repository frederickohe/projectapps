import os

# Loop from 1 to 30 to create folders
for i in range(1, 31):
    folder_name = f"Cust {i:02}"  # Format number with leading zeros (01, 02, etc.)
    os.makedirs(folder_name, exist_ok=True)  # Create folder, skip if already exists
    print(f"Folder '{folder_name}' created.")

print("All folders created successfully.")
