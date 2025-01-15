import os
import pdfplumber
from openai import OpenAI
from docx import Document


# Function to read the contents of a pdf file and return it as a string
def read_pdf(file_path):
    full_text = []
    with pdfplumber.open(file_path) as pdf:
        for page in pdf.pages:
            full_text.append(page.extract_text())
    return "\n".join(full_text)


# Initialize OpenAI client
client = OpenAI(
    api_key=os.environ.get("OPENAI_API_KEY"),
)

# Read the first PDF document (replace with the actual file path)
document_1 = read_pdf("findocs/document_1.pdf")
document_2 = read_pdf("findocs/document_2.pdf")

# Define the system and user prompts
system_message = """Your task is to function as an advanced finance documents analyser and investigator"""
user_prompt_1 = f"""
You will analyze two consecutive financial documents: a pay slip and a bank statement. Follow these steps to extract key details and assess their correspondence:

### Document Details Extraction:
1. Pay Slip: Extract and summarize:
   - Name of the user
   - Employer or organization
   - Date of issuance
   - Net salary amount
   - Deductions and breakdown (if available)
   - Any other relevant details

2. Bank Statement: Extract and summarize:
   - Name of the account holder
   - Bank name
   - Statement period
   - Salary deposit date(s)
   - Amount deposited
   - Any additional relevant transactions


### Accuracy Score:
   - Provide a score out of 100% based on how well the pay slip corresponds with the bank statement.

### Score Justification:
   - Justify the score by explaining areas of agreement or discrepancy.


Financial Documents:
I will attach the first document here (Pay Slip):
{document_1}

The second document also document here (Bank Statement):
{document_2}

"""

# Send the first document to OpenAI
chat_completion = client.chat.completions.create(
    messages=[
        {"role": "system", "content": system_message},
        {"role": "user", "content": user_prompt_1},
    ],
    model="gpt-4",
)

# Extract and print the response content
response_message = chat_completion.choices[0].message.content
print(response_message)
