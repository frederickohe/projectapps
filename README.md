# Mesh Labs Pay Trace  

#### **Project Name**: AI Agent for Financial Data Tracing  

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
- ChatGPT API (chatgpto1, chatgpt omni mini)  
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

## **Fine-Tuning Process**  

##### **1. Dataset Harvesting**
The process begins with gathering raw financial documents from trusted sources. This includes:  
- Collecting various pay slips in different formats.
- Extracting bank statements containing transactional records.
- Creating synthetic datasets for diverse and balanced samples.

##### **2. Data Preparation**
The harvested data is preprocessed and structured in JSONL format to make it suitable for fine-tuning. Key steps include:
- Parsing document content and extracting relevant fields (e.g., salary amount, bank balance).
- Cleaning and normalizing text to ensure consistent formatting.
- Converting the parsed data into JSONL entries, where each line represents a JSON object with labeled fields.

Example JSONL entry:
```json
{"pay_slip_id": "12345", "employee_name": "John Doe", "salary": "5000", "date": "2025-01-01", "employer": "TechCorp"}
```

##### **3. Fine-Tuning**
The fine-tuning process uses the prepared JSONL dataset to adapt a pre-trained LLM for financial data tracing. Steps:
- Load the pre-trained language model using a compatible library (e.g., Hugging Face Transformers).
- Configure training parameters such as learning rate, batch size, and epochs.
- Execute the fine-tuning script with appropriate training data.

Example:
```bash
python main.py --train data.jsonl --model fine_tuned_model
```

##### **4. Resultant Fine-Tuned Model**
The output of the fine-tuning process is a custom model capable of:
- Tracing document similarities with high accuracy.
- Detecting patterns for fraud prevention.
- Generating proof-based insights for document verification.

The fine-tuned model is stored in the `fine_tuned_model/` directory and can be loaded for inference using `main.py` or other custom scripts.

---

#### **Contributing**  
Contributions are welcome! Please fork the repository and submit a pull request for review.

