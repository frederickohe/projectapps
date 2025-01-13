

# Mesh Labs Pay Trace  

#### **Project Name**: Ai Agent for Financial Data Tracing  
#### **Description**  
Mesh Labs Pay Trace is an AI-powered project designed to fine-tune a large language model (LLM) on financial documents for tracing similarities and providing proof-based insights into future documents. The system uses structured and unstructured financial data such as pay slips, bank statements, and JSONL-based datasets to learn patterns and relationships in financial records, enhancing document verification and fraud detection.  

---

#### **Project Structure**  
The project directory is organized as follows:  
- **`Salary_Slip/`**: Directory containing pay slip documents used for model training and testing.  
- **`Bank_Statement/`**: Directory containing bank statement records for analysis and pattern detection.  
- **`Final_Data/`**: The final curated and processed dataset used in model fine-tuning and validation.  
- **`main.py`**: The main script that implements data loading, fine-tuning the LLM, and tracing document similarities.  
- **`data.jsonl`**: A JSONL-formatted file containing structured financial data entries for training and evaluation.  

---

#### **Dependencies**  
- Python 3.x  
- Chatgpt API ( chatgpto1, chatgpt omni mini ) 
- Additional libraries: jsonlines, os, re  

---

#### **Installation**  
1. Clone this repository:  
   ```bash
   git clone https://github.com/your-repository-url/mesh-labs-pay-trace.git
   cd Meshlabspaytrace
   ```  
2. Install the required packages:  
   ```bash
   pip install -r requirements.txt
   ```

---

#### **Contributing**  
Contributions are welcome! Please fork the repository and submit a pull request for review.  

---
